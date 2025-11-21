package br.com.caixa.caixaverso.domain.dto;


import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@JsonbPropertyOrder({
        "produto",
        "data",
        "quantidadeSimulacoes",
        "mediaValorFinal"
})
public class ProdutoPorDiaDTO {

    private String produto;
    private String data;
    private Long quantidadeSimulacoes;

    @JsonbNumberFormat("#0.00")
    private BigDecimal  mediaValorFinal;

    public ProdutoPorDiaDTO() {}

    public ProdutoPorDiaDTO(String produtoNome, String dia, Long qtdSimulacoes, Double mediaValorFinal) {
        this.produto = produtoNome;
        this.data = dia;
        this.quantidadeSimulacoes = qtdSimulacoes;
        setMediaValorFinal(mediaValorFinal);
    }

    public String getProdutoNome() {
        return produto;
    }

    public void setProdutoNome(String produtoNome) {
        this.produto = produtoNome;
    }

    public String getDia() {
        return data;
    }

    public void setDia(String dia) {
        this.data = dia;
    }

    public Long getQtdSimulacoes() {
        return quantidadeSimulacoes;
    }

    public void setQtdSimulacoes(Long qtdSimulacoes) {
        this.quantidadeSimulacoes = qtdSimulacoes;
    }

    public BigDecimal  getMediaValorFinal() {
        return mediaValorFinal;
    }

    public void setMediaValorFinal(Double mediaValorFinal) {
        if (mediaValorFinal == null) {
            this.mediaValorFinal = null;
        } else {
            this.mediaValorFinal = BigDecimal
                    .valueOf(mediaValorFinal)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }
}
