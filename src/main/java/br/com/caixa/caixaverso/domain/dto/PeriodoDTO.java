package br.com.caixa.caixaverso.domain.dto;

public class PeriodoDTO {
    private String inicio;
    private String fim;

    public PeriodoDTO() {}

    public PeriodoDTO(String inicio, String fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getInicio() { return inicio; }
    public void setInicio(String inicio) { this.inicio = inicio; }

    public String getFim() { return fim; }
    public void setFim(String fim) { this.fim = fim; }
}
