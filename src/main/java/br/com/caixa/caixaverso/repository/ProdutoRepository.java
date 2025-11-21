package br.com.caixa.caixaverso.repository;

import br.com.caixa.caixaverso.domain.entity.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {


    public List<Produto> findByTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return List.of();
        }
        return find("LOWER(tipo) = ?1", tipo.toLowerCase(Locale.ROOT)).list();
    }


    public Produto findByNomeIgnoreCase(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        return find("LOWER(nome) = ?1", nome.toLowerCase(Locale.ROOT)).firstResult();
    }


    public List<Produto> findRecomendadosPorPerfil(String perfil) {
        return find("perfilRecomendado = ?1", perfil).list();
    }
}
