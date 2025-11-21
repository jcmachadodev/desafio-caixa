package br.com.caixa.caixaverso.service.strategy;

import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.domain.dto.SimulationResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LciSimulationStrategy implements SimulationStrategy {

    @Override
    public String getTipoProdutoSuportado() {
        return "LCI";
    }

    @Override
    public SimulationResult simulate(Produto produto, double valor, int prazoMeses) {
        double taxaAnual = produto.rentabilidade;
        double anos = prazoMeses / 12.0;

        double valorFinal = valor * Math.pow(1 + taxaAnual, anos);

        return new SimulationResult(valorFinal, taxaAnual, prazoMeses);
    }
}
