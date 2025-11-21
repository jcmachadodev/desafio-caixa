package br.com.caixa.caixaverso.domain.dto;

import java.util.List;

public class TelemetriaReportDTO {
    private List<TelemetriaDTO> servicos;
    private PeriodoDTO periodo;

    public TelemetriaReportDTO() {}

    public TelemetriaReportDTO(List<TelemetriaDTO> servicos, PeriodoDTO periodo) {
        this.servicos = servicos;
        this.periodo = periodo;
    }

    public List<TelemetriaDTO> getServicos() { return servicos; }
    public void setServicos(List<TelemetriaDTO> servicos) { this.servicos = servicos; }

    public PeriodoDTO getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoDTO periodo) { this.periodo = periodo; }
}
