package br.com.caixa.caixaverso.mapper;

import br.com.caixa.caixaverso.domain.entity.Cliente;
import br.com.caixa.caixaverso.domain.dto.ClienteDTO;

public final class ClienteMapper {

    private ClienteMapper() {}

    public static ClienteDTO toDto(Cliente c) {
        if (c == null) return null;
        return new ClienteDTO(
                c.id,
                c.nome,
                c.perfil,
                c.pontuacao
        );
    }

    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        Cliente c = new Cliente();
        if (dto.getId() != null) {
            c.id = dto.getId();
        }
        c.nome = dto.getNome();
        c.perfil = dto.getPerfil();
        c.pontuacao = dto.getPontuacao();
        return c;
    }
}

