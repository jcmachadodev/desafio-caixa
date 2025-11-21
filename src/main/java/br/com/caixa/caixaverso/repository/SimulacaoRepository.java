package br.com.caixa.caixaverso.repository;

import br.com.caixa.caixaverso.domain.dto.ProdutoPorDiaDTO;
import br.com.caixa.caixaverso.domain.entity.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {

    @Inject
    EntityManager em;


    public List<Simulacao> listarSimulacoes() {
        List<Simulacao>  a =   listAll(Sort.by("dataSimulacao"));

        return a;

    }

    public List<Simulacao> findByClienteId(Long clienteId) {
        if (clienteId == null) return List.of();
        return find("cliente.id = ?1 ORDER BY dataSimulacao DESC", clienteId).list();
    }


    public List<ProdutoPorDiaDTO> findSimulacoesPorProdutoDia() {

        String sql = "SELECT produto_nome, date(data_simulacao) as dia, COUNT(*) as qtd, AVG(valor_final) as media_valor_final "
                + "FROM Simulacao "
                + "GROUP BY produto_nome, date(data_simulacao) "
                + "ORDER BY produto_nome, dia DESC";

        Query q = em.createNativeQuery(sql);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<ProdutoPorDiaDTO> result = new ArrayList<>();
        for (Object[] r : rows) {
            String produtoNome = r[0] != null ? r[0].toString() : null;
            String dia = r[1] != null ? r[1].toString() : null;
            Long qtd = r[2] != null ? ((Number) r[2]).longValue() : 0L;
            Double media = r[3] != null ?  ((Number) r[3]).doubleValue() : 0.0;

            ProdutoPorDiaDTO dto = new ProdutoPorDiaDTO(produtoNome, dia, qtd, media);
            result.add(dto);
        }
        return result;
    }



}
