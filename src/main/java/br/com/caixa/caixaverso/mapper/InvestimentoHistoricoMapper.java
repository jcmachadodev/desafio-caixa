package br.com.caixa.caixaverso.mapper;


import br.com.caixa.caixaverso.domain.entity.InvestimentoHistorico;
import br.com.caixa.caixaverso.domain.dto.InvestimentoHistoricoDTO;

public final class InvestimentoHistoricoMapper {

    private InvestimentoHistoricoMapper() {}

    public static InvestimentoHistoricoDTO toDto(InvestimentoHistorico e) {
        if (e == null) return null;
        Long clienteId = (e.cliente != null) ? e.cliente.id : null;
        Long produtoID = (e.produto != null) ? e.produto.id : null;
        return new InvestimentoHistoricoDTO(
                e.id,
                clienteId,
                produtoID,
                e.tipo,
                e.valor,
                e.rentabilidade,
                e.data
        );
    }

}
