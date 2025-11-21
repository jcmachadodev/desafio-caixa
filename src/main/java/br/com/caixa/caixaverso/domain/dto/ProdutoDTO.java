package br.com.caixa.caixaverso.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private String tipo;
    private Double rentabilidade;
    private String risco;
    private String perfilRecomendado;

    public ProdutoDTO() {}

    public ProdutoDTO(Long id, String nome, String tipo, Double rentabilidade, String risco, String perfilRecomendado) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.rentabilidade = rentabilidade;
        this.risco = risco;
        this.perfilRecomendado = perfilRecomendado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotBlank
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @NotNull
    @PositiveOrZero
    public Double getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(Double rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    @NotBlank
    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    @NotBlank
    public String getPerfilRecomendado() {
        return perfilRecomendado;
    }

    public void setPerfilRecomendado(String perfilRecomendado) {
        this.perfilRecomendado = perfilRecomendado;
    }
}
