package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.InvestimentoHistoricoDTO;
import br.com.caixa.caixaverso.infra.exception.NotFoundException;
import br.com.caixa.caixaverso.repository.InvestimentoHistoricoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class InvestimentoHistoricoService {

    @Inject
    InvestimentoHistoricoRepository historicoRepository;

    public List<InvestimentoHistoricoDTO> listarPorCliente(Long clienteId) {
        if (clienteId == null) throw new IllegalArgumentException("clienteId é obrigatório");

        List<InvestimentoHistoricoDTO> list = historicoRepository.findByClienteIdAsDTO(clienteId);
        if (list == null || list.isEmpty()) {
            throw new NotFoundException("Nenhum investimento encontrado para o cliente: " + clienteId);
        }
        return list;
    }
}
