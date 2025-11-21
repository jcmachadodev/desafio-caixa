package br.com.caixa.caixaverso.domain.dto;


import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.math.BigDecimal;

@JsonbPropertyOrder({
        "valorFinal",
        "rentabilidadeEfetiva",
        "prazoMeses"
})
public class ResultadoSimulacaoDTO {

    @JsonbNumberFormat("#0.00")
    private BigDecimal valorFinal;
     @JsonbNumberFormat("#0.00")
    private BigDecimal rentabilidadeEfetiva;
    private Integer prazoMeses;

    public ResultadoSimulacaoDTO() {}

    public BigDecimal getValorFinal() { return valorFinal; }
    public void setValorFinal(BigDecimal valorFinal) { this.valorFinal = valorFinal; }

    public BigDecimal getRentabilidadeEfetiva() { return rentabilidadeEfetiva; }
    public void setRentabilidadeEfetiva(BigDecimal rentabilidadeEfetiva) { this.rentabilidadeEfetiva = rentabilidadeEfetiva; }

    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
}
