package br.com.caixa.caixaverso.service.strategy;

import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.domain.dto.SimulationResult;

public interface SimulationStrategy {

    String getTipoProdutoSuportado();

    SimulationResult simulate(Produto produto, double valor, int prazoMeses);
}
