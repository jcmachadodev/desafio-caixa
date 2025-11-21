package br.com.caixa.caixaverso.domain.dto;


import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String perfil;
    private Integer pontuacao;

    public ClienteDTO() {}

    public ClienteDTO(Long id, String nome, String perfil, Integer pontuacao) {
        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
        this.pontuacao = pontuacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotBlank
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
