package br.com.caixa.caixaverso.domain.dto;


import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
        "clienteId",
        "perfil",
        "pontuacao",
        "descricao"
})
public class PerfilRiscoDTO {
    private Long clienteId;
    private String descricao;
    private String perfil;
    private Integer pontuacao;


    public PerfilRiscoDTO() {}

    public PerfilRiscoDTO(Long clienteId, String descricao, String perfil, Integer pontuacao) {
        this.clienteId = clienteId;
        this.descricao = descricao;
        this.perfil = perfil;
        this.pontuacao = pontuacao;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }
}
