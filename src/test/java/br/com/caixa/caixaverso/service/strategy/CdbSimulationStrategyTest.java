package br.com.caixa.caixaverso.service.strategy;

import br.com.caixa.caixaverso.domain.dto.SimulationResult;
import br.com.caixa.caixaverso.domain.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CdbSimulationStrategyTest {

    private CdbSimulationStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new CdbSimulationStrategy();
    }

    @Test
    void getTipoProdutoSuportado_deveRetornarCDB() {
        assertEquals("CDB", strategy.getTipoProdutoSuportado());
    }

    @Test
    void simulate_12meses_deveCalcularCorretamente() {
        Produto produto = new Produto("CDB Teste", "CDB", 0.12, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 10000.0, 12);

        assertNotNull(result);
        assertEquals(11200.0, result.valorFinal(), 50.0);
        assertEquals(0.12, result.rentabilidadeEfetiva());
        assertEquals(12, result.prazoMeses());
    }

    @Test
    void simulate_24meses_deveCalcularComJurosCompostos() {
        Produto produto = new Produto("CDB Teste", "CDB", 0.10, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 5000.0, 24);


        assertEquals(6050.0, result.valorFinal(), 50.0);
        assertEquals(24, result.prazoMeses());
    }

    @Test
    void simulate_6meses_deveCalcularProporcional() {
        Produto produto = new Produto("CDB Teste", "CDB", 0.12, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 10000.0, 6);


        assertTrue(result.valorFinal() > 10000.0);
        assertTrue(result.valorFinal() < 11200.0);
        assertEquals(6, result.prazoMeses());
    }

    @Test
    void simulate_valorMinimo_deveCalcularSemErro() {
        Produto produto = new Produto("CDB Teste", "CDB", 0.08, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 100.0, 12);

        assertNotNull(result);
        assertTrue(result.valorFinal() > 100.0);
    }

    @Test
    void simulate_rentabilidadeZero_deveRetornarValorInicial() {
        Produto produto = new Produto("CDB Teste", "CDB", 0.0, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 5000.0, 12);

        assertEquals(5000.0, result.valorFinal(), 0.01);
    }
}