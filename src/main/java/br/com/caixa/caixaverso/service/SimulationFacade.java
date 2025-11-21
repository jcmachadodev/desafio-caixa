package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.*;
import br.com.caixa.caixaverso.domain.entity.Simulacao;
import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.domain.entity.Cliente;
import br.com.caixa.caixaverso.infra.exception.BusinessException;
import br.com.caixa.caixaverso.infra.exception.NotFoundException;
import br.com.caixa.caixaverso.mapper.SimulacaoMapper;
import br.com.caixa.caixaverso.repository.ProdutoRepository;
import br.com.caixa.caixaverso.repository.ClienteRepository;
import br.com.caixa.caixaverso.repository.SimulacaoRepository;
import br.com.caixa.caixaverso.repository.TelemetriaRepository;
import br.com.caixa.caixaverso.service.strategy.SimulationStrategy;
import br.com.caixa.caixaverso.service.factory.SimulationStrategyFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

@ApplicationScoped
public class SimulationFacade {

    private static final Logger LOG = Logger.getLogger(SimulationFacade.class);

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    SimulacaoRepository simulacaoRepository;

    @Inject
    TelemetriaRepository telemetriaRepository;

    @Inject
    SimulationStrategyFactory strategyFactory;

    @Transactional
    public SimulacaoResponseDTO simular(@NotNull @Valid SimulacaoRequestDTO request) {

        Cliente cliente = clienteRepository.findByIdOrNull(request.getClienteId());
        if (cliente == null) {
           throw new NotFoundException("Cliente não encontrado: " + request.getClienteId());
        }

        List<Produto> candidatos = produtoRepository.findByTipo(request.getTipoProduto());
        Produto produto = (candidatos == null || candidatos.isEmpty()) ? null : candidatos.get(0);
        if (produto == null) {
            throw new BusinessException("Tipo de produto inválido: " + request.getTipoProduto());
        }

        SimulationStrategy strategy = strategyFactory.getStrategy(produto.tipo);
        SimulationResult result = strategy.simulate(produto, request.getValor(), request.getPrazoMeses());

        Simulacao simulacao = SimulacaoMapper.toEntity(request, cliente, produto, BigDecimal.valueOf(result.valorFinal()));
        simulacaoRepository.persist(simulacao);

        return SimulacaoMapper.toResponse(simulacao,produto, result);
    }

    public List<SimulacaoListDTO> listarSimulacoes() {
        List<Simulacao> all = simulacaoRepository.listarSimulacoes();

        if (all == null || all.isEmpty()) {
            throw new NotFoundException("Nenhuma simulação encontrada");
        }

        return all.stream()
                .map(SimulacaoMapper::toListDTO)
                .collect(Collectors.toList());
    }

    public List<ProdutoPorDiaDTO> listarSimulacoesPorProdutoDia() {
        return simulacaoRepository.findSimulacoesPorProdutoDia();
    }

    public List<SimulacaoListDTO> listarSimulacoesPorCliente(@NotNull Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("clienteId é obrigatório");
        }
        List<Simulacao> list = simulacaoRepository.findByClienteId(clienteId);


        if (list == null || list.isEmpty()) {
            throw new NotFoundException("Nenhuma simulação encontrada para o cliente: " + clienteId);
        }

        return list.stream()
                .map(SimulacaoMapper::toListDTO)
                .collect(Collectors.toList());
    }




}
