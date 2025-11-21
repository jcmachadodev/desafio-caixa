package br.com.caixa.caixaverso.domain.dto;


import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

import java.math.BigDecimal;

@JsonbPropertyOrder({
        "id",
        "nome",
        "tipo",
        "rentabilidade",
        "risco"
})
public class ProdutoValidadoDTO {
    private Long id;
    private String nome;
    private String tipo;

    @JsonbNumberFormat("#0.00")
    private BigDecimal rentabilidade;
    private String risco;

    public ProdutoValidadoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getRentabilidade() { return rentabilidade; }
    public void setRentabilidade(BigDecimal rentabilidade) { this.rentabilidade = rentabilidade; }

    public String getRisco() { return risco; }
    public void setRisco(String risco) { this.risco = risco; }
}
