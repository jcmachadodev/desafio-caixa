package br.com.caixa.caixaverso.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "InvestimentoHistorico")
public class InvestimentoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    public Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    public Produto produto;

    @Column(nullable = false)
    public String tipo;

    @Column(nullable = false)
    public Double valor;

    public Double rentabilidade;

    @Column(nullable = false)
    public String data;

    public InvestimentoHistorico() {}

    public InvestimentoHistorico(
            Cliente cliente,
            Produto produto,
            String tipo,
            Double valor,
            Double rentabilidade,
            String data
    ) {
        this.cliente = cliente;
        this.produto = produto;
        this.tipo = tipo;
        this.valor = valor;
        this.rentabilidade = rentabilidade;
        this.data = data;
    }
}