package br.com.caixa.caixaverso.repository;

import br.com.caixa.caixaverso.domain.dto.InvestimentoHistoricoDTO;
import br.com.caixa.caixaverso.domain.entity.InvestimentoHistorico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class InvestimentoHistoricoRepository implements PanacheRepository<InvestimentoHistorico> {

    @Inject
    EntityManager em;

    public long countOpsAll(Long clienteId) {
        String sql = "SELECT COUNT(*) FROM InvestimentoHistorico WHERE cliente_id = :clienteId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("clienteId", clienteId);
        Number single = (Number) q.getSingleResult();
        return single != null ? single.longValue() : 0L;
    }

    public double sumVolumeAll(Long clienteId) {
        String sql = "SELECT SUM(valor) FROM InvestimentoHistorico WHERE cliente_id = :clienteId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("clienteId", clienteId);
        Object res = q.getSingleResult();
        if (res == null) return 0.0;
        return ((Number) res).doubleValue();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> dailyTotalsAll(Long clienteId) {
        String sql = "SELECT date(data) as dia, SUM(valor) as total " +
                "FROM InvestimentoHistorico " +
                "WHERE cliente_id = :clienteId " +
                "GROUP BY date(data) " +
                "ORDER BY date(data) ASC";
        Query q = em.createNativeQuery(sql);
        q.setParameter("clienteId", clienteId);
        return (List<Object[]>) q.getResultList();
    }

    public int countDistinctTypesAll(Long clienteId) {
        String sql = "SELECT COUNT(DISTINCT tipo) FROM InvestimentoHistorico WHERE cliente_id = :clienteId";
        Query q = em.createNativeQuery(sql);
        q.setParameter("clienteId", clienteId);
        Number single = (Number) q.getSingleResult();
        return single != null ? single.intValue() : 0;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Double> volumeByTypeAll(Long clienteId) {
        String sql = "SELECT tipo, SUM(valor) as volumeTipo " +
                "FROM InvestimentoHistorico " +
                "WHERE cliente_id = :clienteId " +
                "GROUP BY tipo";
        Query q = em.createNativeQuery(sql);
        q.setParameter("clienteId", clienteId);
        List<Object[]> rows = (List<Object[]>) q.getResultList();
        Map<String, Double> map = new HashMap<>();
        for (Object[] r : rows) {
            String tipo = r[0] != null ? r[0].toString() : null;
            Double vol = r[1] != null ? ((Number) r[1]).doubleValue() : 0.0;
            map.put(tipo, vol);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> findHistoricoRawAll(Long clienteId) {
        String sql = "SELECT id, tipo, valor, rentabilidade, date(data) FROM InvestimentoHistorico " +
                "WHERE cliente_id = :clienteId " +
                "ORDER BY date(data) ASC";
        Query q = em.createNativeQuery(sql);
        q.setParameter("clienteId", clienteId);
        return (List<Object[]>) q.getResultList();
    }

    public double avgRentabilidadeAll(Long clienteId) {
        if (clienteId == null) return 0.0;
        Number n = (Number) getEntityManager()
                .createQuery("SELECT AVG(h.rentabilidade) FROM InvestimentoHistorico h WHERE h.cliente.id = :cid")
                .setParameter("cid", clienteId)
                .getSingleResult();
        return n != null ? n.doubleValue() : 0.0;
    }

    public List<InvestimentoHistoricoDTO> findByClienteIdAsDTO(Long clienteId) {
        if (clienteId == null) return List.of();

        String sql = "SELECT id, tipo, valor, rentabilidade, data FROM InvestimentoHistorico "
                + "WHERE cliente_id = ?1 "
                + "ORDER BY data DESC";

        Query q = em.createNativeQuery(sql);
        q.setParameter(1, clienteId);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<InvestimentoHistoricoDTO> list = new ArrayList<>();
        for (Object[] r : rows) {
            Long id = r[0] != null ? ((Number) r[0]).longValue() : null;
            String tipo = r[1] != null ? r[1].toString() : null;
            Double valor = r[2] != null ? ((Number) r[2]).doubleValue() : null;
            Double rent = r[3] != null ? ((Number) r[3]).doubleValue() : null;
            String data = r[4] != null ? r[4].toString() : null;

            list.add(new InvestimentoHistoricoDTO(id, tipo, valor, rent, data));
        }
        return list;
    }
}
