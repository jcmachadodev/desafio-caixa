package br.com.caixa.caixaverso.domain.dto;

public class TelemetriaDTO {
    private String nome;
    private Long quantidadeChamadas;
    private Long mediaTempoRespostaMs;

    public TelemetriaDTO() {}

    public TelemetriaDTO(String nome, Long quantidadeChamadas, Long mediaTempoRespostaMs) {
        this.nome = nome;
        this.quantidadeChamadas = quantidadeChamadas;
        this.mediaTempoRespostaMs = mediaTempoRespostaMs;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Long getQuantidadeChamadas() { return quantidadeChamadas; }
    public void setQuantidadeChamadas(Long quantidadeChamadas) { this.quantidadeChamadas = quantidadeChamadas; }

    public Long getMediaTempoRespostaMs() { return mediaTempoRespostaMs; }
    public void setMediaTempoRespostaMs(Long mediaTempoRespostaMs) { this.mediaTempoRespostaMs = mediaTempoRespostaMs; }
}
