package br.com.caixa.caixaverso.domain.dto;

public record SimulationResult(
        Double valorFinal,
        Double rentabilidadeEfetiva,
        int prazoMeses
) {}