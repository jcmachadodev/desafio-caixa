package br.com.caixa.caixaverso.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Produto")
public class Produto  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String tipo;

    @Column(nullable = false)
    public Double rentabilidade;

    @Column(nullable = false)
    public String risco;

    @Column(name = "perfil_recomendado", nullable = false)
    public String perfilRecomendado;

    public Produto() {}

    public Produto(String nome, String tipo, Double rentabilidade, String risco, String perfilRecomendado) {
        this.nome = nome;
        this.tipo = tipo;
        this.rentabilidade = rentabilidade;
        this.risco = risco;
        this.perfilRecomendado = perfilRecomendado;
    }
}
