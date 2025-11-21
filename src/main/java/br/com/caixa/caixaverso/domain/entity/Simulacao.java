package br.com.caixa.caixaverso.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INTEGER")
    public Long id;

    @Column(name = "produto_nome", nullable = false)
    public String produtoNome;

    @Column(name = "valor_investido", nullable = false)
    public BigDecimal valorInvestido;

    @Column(name = "valor_final", nullable = false)
    public BigDecimal valorFinal;

    @Column(name = "prazo_meses", nullable = false)
    public Integer prazoMeses;

    @Column(name = "data_simulacao", nullable = false)
    public String dataSimulacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    public Cliente cliente;

    public Simulacao() {}

    public Simulacao(Cliente cliente, String produtoNome, BigDecimal valorInvestido, BigDecimal valorFinal, Integer prazoMeses, String dataSimulacao) {
        this.cliente = cliente;
        this.produtoNome = produtoNome;
        this.valorInvestido = valorInvestido;
        this.valorFinal = valorFinal;
        this.prazoMeses = prazoMeses;
        this.dataSimulacao = dataSimulacao;
    }
}
