package br.com.caixa.caixaverso.domain.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Telemetria")
public class Telemetria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "servico_nome", nullable = false)
    public String servicoNome;

    @Column(name = "tempo_resposta_ms", nullable = false)
    public Integer tempoRespostaMs;

    @Column(name = "data_chamada", nullable = false)
    public String dataChamada;

    @Column(name = "http_status", nullable = false)
    public Integer httpStatus;

    @Column(name = "http_method", nullable = false)
    public String httpMethod;

    @Column(name = "path", nullable = false)
    public String path;

    public Telemetria() {}

    public Telemetria(String servicoNome,
                      Integer tempoRespostaMs,
                      String dataChamada,
                      Integer httpStatus,
                      String httpMethod,
                      String path) {

        this.servicoNome = servicoNome;
        this.tempoRespostaMs = tempoRespostaMs;
        this.dataChamada = dataChamada;
        this.httpStatus = httpStatus;
        this.httpMethod = httpMethod;
        this.path = path;
    }
}
