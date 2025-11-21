package br.com.caixa.caixaverso.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cliente")
public class Cliente  {

    @Id
    public Long id;

    public String nome;
    public String perfil;
    public Integer pontuacao;

    public Cliente() {}

    public Cliente(Long id, String nome, String perfil, Integer pontuacao) {
        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
        this.pontuacao = pontuacao;
    }
}