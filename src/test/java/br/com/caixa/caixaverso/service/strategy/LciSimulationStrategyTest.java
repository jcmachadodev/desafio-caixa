package br.com.caixa.caixaverso.service.strategy;

import br.com.caixa.caixaverso.domain.dto.SimulationResult;
import br.com.caixa.caixaverso.domain.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LciSimulationStrategyTest {

    private LciSimulationStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new LciSimulationStrategy();
    }

    @Test
    void getTipoProdutoSuportado_deveRetornarLCI() {
        assertEquals("LCI", strategy.getTipoProdutoSuportado());
    }

    @Test
    void simulate_12meses_deveCalcularSemIR() {

        Produto produto = new Produto("LCI Teste", "LCI", 0.10, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 10000.0, 12);


        assertEquals(11000.0, result.valorFinal(), 50.0);
        assertEquals(0.10, result.rentabilidadeEfetiva());
        assertEquals(12, result.prazoMeses());
    }

    @Test
    void simulate_24meses_deveAplicarJurosCompostos() {
        Produto produto = new Produto("LCI Teste", "LCI", 0.09, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 8000.0, 24);


        assertEquals(9505.0, result.valorFinal(), 100.0);
    }
}