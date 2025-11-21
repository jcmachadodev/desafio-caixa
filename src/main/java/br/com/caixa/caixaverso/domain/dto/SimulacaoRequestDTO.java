package br.com.caixa.caixaverso.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Envelope de solicitação para simulação de investimento")
public class SimulacaoRequestDTO {
    @Schema(description = "ID do cliente", example = "1")
    private Long clienteId;
    @Schema(description = "Valor do investimento", example = "10000.00")
    private Double valor;
    @Schema(description = "Prazo em meses", example = "12")
    private Integer prazoMeses;
    @Schema(description = "Tipo de produto. Ex: CDB, LCI, LCA, Tesouro, Fundo",
            example = "CDB")
    private String tipoProduto;

    public SimulacaoRequestDTO() {}

    public SimulacaoRequestDTO(Long clienteId, Double valor, Integer prazoMeses, String tipoProduto) {
        this.clienteId = clienteId;
        this.valor = valor;
        this.prazoMeses = prazoMeses;
        this.tipoProduto = tipoProduto;
    }

    @NotNull
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @NotNull
    @Positive
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @NotNull
    @Min(1)
    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    @NotBlank
    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
}
