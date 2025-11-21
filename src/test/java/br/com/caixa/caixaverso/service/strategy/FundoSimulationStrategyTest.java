package br.com.caixa.caixaverso.service.strategy;

import br.com.caixa.caixaverso.domain.dto.SimulationResult;
import br.com.caixa.caixaverso.domain.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FundoSimulationStrategyTest {

    private FundoSimulationStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new FundoSimulationStrategy();
    }

    @Test
    void getTipoProdutoSuportado_deveRetornarFundo() {
        assertEquals("Fundo", strategy.getTipoProdutoSuportado());
    }

    @Test
    void simulate_deveDescontarTaxaAdministracao() {

        Produto produto = new Produto("Fundo Teste", "Fundo", 0.18, "Alto", "AGRESSIVO");

        SimulationResult result = strategy.simulate(produto, 10000.0, 12);


        assertTrue(result.valorFinal() < 11800.0); // deve ser menor que 18%
        assertTrue(result.valorFinal() > 11000.0); // mas maior que 10%
        assertEquals(12, result.prazoMeses());
    }

    @Test
    void simulate_taxaAdmMaiorQueRentabilidade_deveLimitarAZero() {

        Produto produto = new Produto("Fundo Teste", "Fundo", 0.01, "Baixo", "CONSERVADOR");

        SimulationResult result = strategy.simulate(produto, 5000.0, 12);


        assertEquals(5000.0, result.valorFinal(), 0.01);
    }

    @Test
    void simulate_24meses_deveAplicarJurosCompostos() {
        Produto produto = new Produto("Fundo Teste", "Fundo", 0.22, "Alto", "AGRESSIVO");

        SimulationResult result = strategy.simulate(produto, 10000.0, 24);


        assertEquals(14400.0, result.valorFinal(), 200.0);
    }
}