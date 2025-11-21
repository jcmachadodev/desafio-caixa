package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.*;
import br.com.caixa.caixaverso.domain.entity.Cliente;
import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.domain.entity.Simulacao;
import br.com.caixa.caixaverso.infra.exception.BusinessException;
import br.com.caixa.caixaverso.infra.exception.NotFoundException;
import br.com.caixa.caixaverso.repository.*;
import br.com.caixa.caixaverso.service.factory.SimulationStrategyFactory;
import br.com.caixa.caixaverso.service.strategy.CdbSimulationStrategy;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class SimulationFacadeTest {

    @Inject
    SimulationFacade facade;

    @InjectMock
    ClienteRepository clienteRepository;

    @InjectMock
    ProdutoRepository produtoRepository;

    @InjectMock
    SimulacaoRepository simulacaoRepository;

    @InjectMock
    SimulationStrategyFactory strategyFactory;

    @Test
    void simular_comDadosValidos_devePersistirERetornar() {

        SimulacaoRequestDTO request = new SimulacaoRequestDTO(1L, 10000.0, 12, "CDB");

        Cliente cliente = new Cliente(1L, "João", "CONSERVADOR", 30);
        when(clienteRepository.findByIdOrNull(1L)).thenReturn(cliente);

        Produto produto = new Produto("CDB Caixa", "CDB", 0.12, "Baixo", "CONSERVADOR");
        produto.id = 1L;
        when(produtoRepository.findByTipo("CDB")).thenReturn(List.of(produto));

        CdbSimulationStrategy strategy = new CdbSimulationStrategy();
        when(strategyFactory.getStrategy("CDB")).thenReturn(strategy);

        doNothing().when(simulacaoRepository).persist(any(Simulacao.class));


        SimulacaoResponseDTO response = facade.simular(request);


        assertNotNull(response);
        assertNotNull(response.getProdutoValidado());
        assertEquals("CDB Caixa", response.getProdutoValidado().getNome());
        assertNotNull(response.getResultadoSimulacao());
        assertTrue(response.getResultadoSimulacao().getValorFinal().doubleValue() > 10000.0);

        verify(simulacaoRepository, times(1)).persist(any(Simulacao.class));
    }

    @Test
    void simular_clienteInexistente_deveLancarNotFoundException() {
        SimulacaoRequestDTO request = new SimulacaoRequestDTO(999L, 10000.0, 12, "CDB");

        when(clienteRepository.findByIdOrNull(999L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> facade.simular(request));
    }

    @Test
    void simular_tipoProdutoInvalido_deveLancarBusinessException() {
        SimulacaoRequestDTO request = new SimulacaoRequestDTO(1L, 10000.0, 12, "INVALIDO");

        Cliente cliente = new Cliente(1L, "João", "CONSERVADOR", 30);
        when(clienteRepository.findByIdOrNull(1L)).thenReturn(cliente);
        when(produtoRepository.findByTipo("INVALIDO")).thenReturn(List.of());

        assertThrows(BusinessException.class, () -> facade.simular(request));
    }

    @Test
    void listarSimulacoes_comDados_deveRetornarLista() {
        Simulacao s1 = new Simulacao();
        s1.id = 1L;
        s1.cliente = new Cliente(1L, "João", "CONSERVADOR", 30);
        s1.produtoNome = "CDB Caixa";
        s1.valorInvestido = BigDecimal.valueOf(10000);
        s1.valorFinal = BigDecimal.valueOf(11200);
        s1.prazoMeses = 12;
        s1.dataSimulacao = "2025-01-15T10:00:00Z";

        when(simulacaoRepository.listarSimulacoes()).thenReturn(List.of(s1));

        List<SimulacaoListDTO> result = facade.listarSimulacoes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CDB Caixa", result.get(0).getProduto());
    }

    @Test
    void listarSimulacoes_semDados_deveLancarNotFoundException() {
        when(simulacaoRepository.listarSimulacoes()).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> facade.listarSimulacoes());
    }

    @Test
    void listarSimulacoesPorCliente_comDados_deveRetornarLista() {
        Long clienteId = 1L;
        Simulacao s1 = new Simulacao();
        s1.id = 1L;
        s1.cliente = new Cliente(clienteId, "João", "CONSERVADOR", 30);
        s1.produtoNome = "CDB Caixa";
        s1.valorInvestido = BigDecimal.valueOf(5000);
        s1.valorFinal = BigDecimal.valueOf(5600);
        s1.prazoMeses = 12;
        s1.dataSimulacao = "2025-01-15T10:00:00Z";

        when(simulacaoRepository.findByClienteId(clienteId)).thenReturn(List.of(s1));

        List<SimulacaoListDTO> result = facade.listarSimulacoesPorCliente(clienteId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

}