package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.entity.Telemetria;
import br.com.caixa.caixaverso.repository.TelemetriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

@ApplicationScoped
public class TelemetriaAsync {

    private static final Logger LOG = Logger.getLogger(TelemetriaAsync.class);

    @Inject
    TelemetriaRepository telemetriaRepository;


    @Transactional
    public void persistTelemetry(Telemetria t) {
        try {
            telemetriaRepository.persist(t);
        } catch (Exception e) {
            LOG.warn("Falha ao persistir telemetria assíncrona: " + e.getMessage(), e);
        }
    }
}
