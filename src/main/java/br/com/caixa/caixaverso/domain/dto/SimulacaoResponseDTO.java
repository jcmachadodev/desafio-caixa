package br.com.caixa.caixaverso.domain.dto;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@JsonbPropertyOrder({
        "produtoValidado",
        "resultadoSimulacao",
        "dataSimulacao"
})
@Schema(description = "Resposta contendo o resultado da simulação")
public class SimulacaoResponseDTO {
    @Schema(description = "Produto validado para a simulação")
    private ProdutoValidadoDTO produtoValidado;
    @Schema(description = "Resultado da simulação")
    private ResultadoSimulacaoDTO resultadoSimulacao;
    @Schema(description = "Data/hora da simulação", example = "2025-10-31T14:00:00Z")
    private String dataSimulacao;

    public SimulacaoResponseDTO() {}

    public ProdutoValidadoDTO getProdutoValidado() { return produtoValidado; }
    public void setProdutoValidado(ProdutoValidadoDTO produtoValidado) { this.produtoValidado = produtoValidado; }

    public ResultadoSimulacaoDTO getResultadoSimulacao() { return resultadoSimulacao; }
    public void setResultadoSimulacao(ResultadoSimulacaoDTO resultadoSimulacao) { this.resultadoSimulacao = resultadoSimulacao; }

    public String getDataSimulacao() { return dataSimulacao; }
    public void setDataSimulacao(String dataSimulacao) { this.dataSimulacao = dataSimulacao; }
}
