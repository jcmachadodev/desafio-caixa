package br.com.caixa.caixaverso.repository;

import br.com.caixa.caixaverso.domain.dto.TelemetriaDTO;
import br.com.caixa.caixaverso.domain.entity.Telemetria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TelemetriaRepository implements PanacheRepository<Telemetria> {

    @Inject
    EntityManager em;


    public List<TelemetriaDTO> findAggregatedByServiceBetween(LocalDate start, LocalDate end) {
        String sql = "SELECT servico_nome, COUNT(*) as qtd, AVG(tempo_resposta_ms) as avg_ms " +
                "FROM Telemetria " +
                "WHERE date(data_chamada) BETWEEN :inicio AND :fim " +
                "GROUP BY servico_nome " +
                "ORDER BY servico_nome";

        Query q = em.createNativeQuery(sql);
        q.setParameter("inicio", start.toString());
        q.setParameter("fim", end.toString());

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<TelemetriaDTO> result = new ArrayList<>();
        for (Object[] r : rows) {
            String servico = r[0] != null ? r[0].toString() : null;
            Long qtd = r[1] != null ? ((Number) r[1]).longValue() : 0L;
            Double avg = r[2] != null ? ((Number) r[2]).doubleValue() : 0.0;

            Long avgRounded = BigDecimal.valueOf(avg)
                    .setScale(0, RoundingMode.HALF_UP)
                    .longValue();

            result.add(new TelemetriaDTO(servico, qtd, avgRounded));
        }
        return result;
    }
}

