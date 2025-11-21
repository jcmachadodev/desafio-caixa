package br.com.caixa.caixaverso.mapper;


import br.com.caixa.caixaverso.domain.dto.*;
import br.com.caixa.caixaverso.domain.entity.Simulacao;
import br.com.caixa.caixaverso.domain.entity.Cliente;
import br.com.caixa.caixaverso.domain.entity.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public final class SimulacaoMapper {

    private SimulacaoMapper() {}

    private static final DateTimeFormatter TWO_DECIMAL_INSTANT =
            new DateTimeFormatterBuilder()
                    .appendInstant(2)
                    .toFormatter();

    private static BigDecimal round2(BigDecimal value) {
        if (value == null) return null;
        return value.setScale(2, RoundingMode.HALF_UP);
    }


    private static String formatInstantString(String data) {
        if (data == null) {
            return null;
        }
        try {
            Instant inst = Instant.parse(data);
            return TWO_DECIMAL_INSTANT.format(inst);
        } catch (DateTimeParseException ex) {
             return data;
        }
    }

    public static ProdutoValidadoDTO toProdutoValidadoDTO(Produto p) {
        if (p == null) return null;
        ProdutoValidadoDTO dto = new ProdutoValidadoDTO();
        dto.setId(p.id);
        dto.setNome(p.nome);
        dto.setTipo(p.tipo);
        dto.setRentabilidade(round2(BigDecimal.valueOf(p.rentabilidade)));
        dto.setRisco(p.risco);
        return dto;
    }

    public static ResultadoSimulacaoDTO toResultadoSimulacaoDTO(SimulationResult result, BigDecimal rentabilidadeBase) {
        if (result == null) return null;
        ResultadoSimulacaoDTO dto = new ResultadoSimulacaoDTO();
        dto.setValorFinal(round2(BigDecimal.valueOf(result.valorFinal())));
        dto.setRentabilidadeEfetiva(rentabilidadeBase);
        dto.setPrazoMeses(result.prazoMeses());
        return dto;
    }

    public static SimulacaoResponseDTO toResponse(Simulacao simulacao, Produto produto, SimulationResult result) {
        SimulacaoResponseDTO resp = new SimulacaoResponseDTO();
        resp.setProdutoValidado(toProdutoValidadoDTO(produto));
        resp.setResultadoSimulacao(toResultadoSimulacaoDTO(result, produto != null ? round2(BigDecimal.valueOf(produto.rentabilidade)) : null));
        resp.setDataSimulacao(simulacao != null ? formatInstantString(simulacao.dataSimulacao) : null);
        return resp;
    }

    public static Simulacao toEntity(SimulacaoRequestDTO req, Cliente cliente, Produto produto, BigDecimal valorFinal) {
        if (req == null) return null;
        Simulacao s = new Simulacao();
        s.cliente = cliente;
        s.produtoNome = produto != null ? produto.nome : null;
        s.valorInvestido = BigDecimal.valueOf(req.getValor());
        s.valorFinal = valorFinal;
        s.prazoMeses = req.getPrazoMeses();
        s.dataSimulacao = Instant.now().toString();
        return s;
    }


    public static SimulacaoListDTO toListDTO(Simulacao s) {
        SimulacaoListDTO dto = new SimulacaoListDTO();
        dto.setId(s.id);
        dto.setClienteId(s.cliente != null ? s.cliente.id : null);
        dto.setProduto(s.produtoNome);
        dto.setValorInvestido(round2(s.valorInvestido));
        dto.setValorFinal(round2(s.valorFinal));
        dto.setPrazoMeses(s.prazoMeses);
        dto.setDataSimulacao(formatInstantString(s.dataSimulacao));
        return dto;
    }


}
