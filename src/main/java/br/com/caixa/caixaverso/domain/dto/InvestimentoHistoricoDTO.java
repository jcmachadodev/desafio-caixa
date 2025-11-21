package br.com.caixa.caixaverso.domain.dto;


import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "id", "tipo", "valor", "rentabilidade", "data" })
public class InvestimentoHistoricoDTO {
    private Long id;
    private Long clienteId;
    private Long produtoId;
    private String tipo;
    private Double valor;
    private Double rentabilidade;
    private String data;

    public InvestimentoHistoricoDTO() {}

    public InvestimentoHistoricoDTO(Long id, Long clienteId, Long produtoId, String tipo, Double valor, Double rentabilidade, String data) {
        this.id = id;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.tipo = tipo;
        this.valor = valor;
        this.rentabilidade = rentabilidade;
        this.data = data;
    }

    public InvestimentoHistoricoDTO(Long id, String tipo, Double valor, Double rent, String data) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(Double rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}