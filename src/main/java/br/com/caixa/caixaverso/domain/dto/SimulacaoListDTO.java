package br.com.caixa.caixaverso.domain.dto;

import jakarta.json.bind.annotation.JsonbNumberFormat;

import java.math.BigDecimal;

public class SimulacaoListDTO {
    private Long id;
    private Long clienteId;
    private String produto;
    @JsonbNumberFormat("#0.00")
    private BigDecimal valorInvestido;
    @JsonbNumberFormat("#0.00")
    private BigDecimal valorFinal;
    private Integer prazoMeses;
    private String dataSimulacao;

    public SimulacaoListDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public BigDecimal getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(BigDecimal valorInvestido) { this.valorInvestido = valorInvestido; }

    public BigDecimal getValorFinal() { return valorFinal; }
    public void setValorFinal(BigDecimal valorFinal) { this.valorFinal = valorFinal; }

    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }

    public String getDataSimulacao() { return dataSimulacao; }
    public void setDataSimulacao(String dataSimulacao) { this.dataSimulacao = dataSimulacao; }
}
