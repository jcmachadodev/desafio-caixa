package br.com.caixa.caixaverso.mapper;


import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.domain.dto.ProdutoDTO;

public final class ProdutoMapper {

    private ProdutoMapper() {}

    public static ProdutoDTO toDto(Produto p) {
        if (p == null) return null;
        return new ProdutoDTO(
                p.id,
                p.nome,
                p.tipo,
                p.rentabilidade,
                p.risco,
                p.perfilRecomendado
        );
    }

    public static Produto toEntity(ProdutoDTO dto) {
        if (dto == null) return null;
        Produto p = new Produto(dto.getNome(), dto.getTipo(), dto.getRentabilidade(), dto.getRisco(), dto.getPerfilRecomendado());
        if (dto.getId() != null) {
            p.id = dto.getId();
        }
        return p;
    }
}

