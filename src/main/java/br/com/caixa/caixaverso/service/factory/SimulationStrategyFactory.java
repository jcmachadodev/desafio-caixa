package br.com.caixa.caixaverso.service.factory;

import br.com.caixa.caixaverso.service.strategy.SimulationStrategy;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SimulationStrategyFactory {

    private static final Logger LOG = Logger.getLogger(SimulationStrategyFactory.class);

    @Inject
    Instance<SimulationStrategy> strategies;

    private Map<String, SimulationStrategy> strategiesByTipo = Collections.emptyMap();

    @PostConstruct
    void init() {
        Map<String, SimulationStrategy> map = new HashMap<>();
        for (SimulationStrategy s : strategies) {
            try {
                String tipo = s.getTipoProdutoSuportado();
                if (tipo == null || tipo.isBlank()) {
                    LOG.warnf("Strategy %s declarou tipo nulo/vazio; será ignorada", s.getClass().getName());
                    continue;
                }
                String key = tipo.toUpperCase(Locale.ROOT);
                if (map.containsKey(key)) {
                    LOG.warnf("Há mais de uma strategy registrada para o tipo '%s' — mantendo %s e ignorando %s",
                            key, map.get(key).getClass().getName(), s.getClass().getName());
                    continue;
                }
                map.put(key, s);
            } catch (Exception ex) {
                LOG.errorf(ex, "Erro ao registrar strategy %s", s.getClass().getName());
            }
        }
        strategiesByTipo = Collections.unmodifiableMap(map);
        LOG.infov("SimulationStrategyFactory inicializada. Strategies registradas: {0}", strategiesByTipo.keySet());
    }

    public SimulationStrategy getStrategy(String tipoProduto) {
        if (tipoProduto == null || tipoProduto.isBlank()) {
            throw new IllegalArgumentException("tipoProduto não pode ser nulo ou vazio");
        }
        SimulationStrategy s = strategiesByTipo.get(tipoProduto.toUpperCase(Locale.ROOT));
        if (s == null) {
            throw new StrategyNotFoundException("Cálculo não suportado para o tipo: " + tipoProduto);
        }
        return s;
    }

    public Map<String, SimulationStrategy> getRegisteredStrategies() {
        return strategiesByTipo;
    }

    public static class StrategyNotFoundException extends RuntimeException {
        public StrategyNotFoundException(String message) {
            super(message);
        }
    }
}
