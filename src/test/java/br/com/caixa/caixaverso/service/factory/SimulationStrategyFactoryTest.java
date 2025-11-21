package br.com.caixa.caixaverso.service.factory;

import br.com.caixa.caixaverso.service.strategy.CdbSimulationStrategy;
import br.com.caixa.caixaverso.service.strategy.FundoSimulationStrategy;
import br.com.caixa.caixaverso.service.strategy.LciSimulationStrategy;
import br.com.caixa.caixaverso.service.strategy.SimulationStrategy;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SimulationStrategyFactoryTest {

    @Inject
    SimulationStrategyFactory factory;

    @Test
    void getStrategy_CDB_deveRetornarCdbStrategy() {
        SimulationStrategy strategy = factory.getStrategy("CDB");

        assertNotNull(strategy);
        assertTrue(strategy instanceof CdbSimulationStrategy);
        assertEquals("CDB", strategy.getTipoProdutoSuportado());
    }

    @Test
    void getStrategy_LCI_deveRetornarLciStrategy() {
        SimulationStrategy strategy = factory.getStrategy("LCI");

        assertNotNull(strategy);
        assertTrue(strategy instanceof LciSimulationStrategy);
    }

    @Test
    void getStrategy_Fundo_deveRetornarFundoStrategy() {
        SimulationStrategy strategy = factory.getStrategy("Fundo");

        assertNotNull(strategy);
        assertTrue(strategy instanceof FundoSimulationStrategy);
    }

    @Test
    void getStrategy_caseInsensitive_deveFuncionar() {
        SimulationStrategy strategy1 = factory.getStrategy("cdb");
        SimulationStrategy strategy2 = factory.getStrategy("CDB");
        SimulationStrategy strategy3 = factory.getStrategy("Cdb");

        assertNotNull(strategy1);
        assertNotNull(strategy2);
        assertNotNull(strategy3);
    }

    @Test
    void getStrategy_tipoInvalido_deveLancarException() {
        assertThrows(
                SimulationStrategyFactory.StrategyNotFoundException.class,
                () -> factory.getStrategy("INVALIDO")
        );
    }

    @Test
    void getStrategy_tipoNulo_deveLancarException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> factory.getStrategy(null)
        );
    }

    @Test
    void getStrategy_tipoVazio_deveLancarException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> factory.getStrategy("")
        );
    }

    @Test
    void getRegisteredStrategies_deveConterTodasStrategies() {
        var strategies = factory.getRegisteredStrategies();

        assertNotNull(strategies);
        assertTrue(strategies.containsKey("CDB"));
        assertTrue(strategies.containsKey("LCI"));
        assertTrue(strategies.containsKey("FUNDO"));
        assertEquals(3, strategies.size());
    }
}