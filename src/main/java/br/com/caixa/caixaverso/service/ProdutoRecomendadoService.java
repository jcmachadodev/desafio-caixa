package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.ProdutoValidadoDTO;
import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.mapper.SimulacaoMapper;
import br.com.caixa.caixaverso.repository.ProdutoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoRecomendadoService {

    @Inject
    ProdutoRepository produtoRepository;

    public List<ProdutoValidadoDTO> recomendar(String perfil) {
        if (perfil == null || perfil.isBlank()) {
            perfil = "CONSERVADOR";
        }

        String perfilUpper = perfil.trim().toUpperCase(Locale.ROOT);

       List<Produto> produtos = produtoRepository.findRecomendadosPorPerfil(perfilUpper);

        return produtos.stream()
                .map(SimulacaoMapper::toProdutoValidadoDTO)
                .collect(Collectors.toList());
    }
}
