package br.com.caixa.caixaverso.service.strategy;

import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.domain.dto.SimulationResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FundoSimulationStrategy implements SimulationStrategy {

    @Override
    public String getTipoProdutoSuportado() {
        return "Fundo";
    }

    @Override
    public SimulationResult simulate(Produto produto, double valor, int prazoMeses) {
        double taxaBrutaAnual = produto.rentabilidade;
        double taxaAdmAnual = 0.02;

        double taxaLiquidaAnual = taxaBrutaAnual - taxaAdmAnual;
        if (taxaLiquidaAnual < 0) {
            taxaLiquidaAnual = 0;
        }

        double anos = prazoMeses / 12.0;
        double valorFinal = valor * Math.pow(1 + taxaLiquidaAnual, anos);

        return new SimulationResult(valorFinal, taxaLiquidaAnual, prazoMeses);
    }
}
