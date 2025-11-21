package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.TelemetriaDTO;
import br.com.caixa.caixaverso.domain.entity.Telemetria;
import br.com.caixa.caixaverso.repository.TelemetriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class TelemetriaService {

    @Inject
    TelemetriaRepository telemetriaRepository;

    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    TelemetriaAsync telemetriaAsync;

    public List<TelemetriaDTO> getAggregatedByService(String inicio, String fim) {
        LocalDate startDate;
        LocalDate endDate;

        if ((inicio == null || inicio.isBlank()) && (fim == null || fim.isBlank())) {

            YearMonth ym = YearMonth.now();
            startDate = ym.atDay(1);
            endDate = ym.atEndOfMonth();
        } else {
            if (inicio == null || inicio.isBlank()) {
                LocalDate parsedFim = LocalDate.parse(fim);
                YearMonth ymFim = YearMonth.from(parsedFim);
                startDate = ymFim.atDay(1);
                endDate = parsedFim;
            } else if (fim == null || fim.isBlank()) {
                LocalDate parsedInicio = LocalDate.parse(inicio);
                YearMonth ymInicio = YearMonth.from(parsedInicio);
                startDate = parsedInicio;
                endDate = ymInicio.atEndOfMonth();
            } else {
                startDate = LocalDate.parse(inicio);
                endDate = LocalDate.parse(fim);
            }
        }


        if (startDate.isAfter(endDate)) {
            LocalDate tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        return telemetriaRepository.findAggregatedByServiceBetween(startDate, endDate);
    }


    public PeriodRange computePeriodRange(String inicio, String fim) {
        LocalDate startDate;
        LocalDate endDate;

        if ((inicio == null || inicio.isBlank()) && (fim == null || fim.isBlank())) {
            YearMonth ym = YearMonth.now();
            startDate = ym.atDay(1);
            endDate = ym.atEndOfMonth();
        } else {
            if (inicio == null || inicio.isBlank()) {
                LocalDate parsedFim = LocalDate.parse(fim);
                YearMonth ymFim = YearMonth.from(parsedFim);
                startDate = ymFim.atDay(1);
                endDate = parsedFim;
            } else if (fim == null || fim.isBlank()) {
                LocalDate parsedInicio = LocalDate.parse(inicio);
                YearMonth ymInicio = YearMonth.from(parsedInicio);
                startDate = parsedInicio;
                endDate = ymInicio.atEndOfMonth();
            } else {
                startDate = LocalDate.parse(inicio);
                endDate = LocalDate.parse(fim);
            }
        }

        if (startDate.isAfter(endDate)) {
            LocalDate tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        return new PeriodRange(startDate, endDate);
    }

    public static final class PeriodRange {
        public final LocalDate start;
        public final LocalDate end;
        public PeriodRange(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
        }
    }


    public void recordAsync(String servicoNome, int tempoMs, int statusHttp, String path, String metodo) {
        Telemetria t = new Telemetria();
        t.servicoNome = servicoNome;
        t.tempoRespostaMs = tempoMs;
        t.dataChamada = Instant.now().toString();

        try {
           t.httpStatus = statusHttp;
            t.path = path;
            t.httpMethod = metodo;
        } catch (NoSuchFieldError | Exception ignore) {

        }

        CompletionStage<Void> cs = managedExecutor.runAsync(() -> {
            try {
                telemetriaAsync.persistTelemetry(t);
            } catch (Exception e) {
                LOG.warn("Erro ao persistir telemetria no executor: " + e.getMessage(), e);
            }
        });


        cs.exceptionally(ex -> {
            LOG.warn("Telemetria assíncrona falhou: " + ex.getMessage(), ex);
            return null;
        });
    }



}

