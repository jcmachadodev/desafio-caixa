package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.entity.Produto;
import br.com.caixa.caixaverso.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProdutoRecomendadoServiceTest {

    private ProdutoRepository produtoRepository;
    private ProdutoRecomendadoService service;

    @BeforeEach
    public void setup() {
        produtoRepository = mock(ProdutoRepository.class);
        service = new ProdutoRecomendadoService();

        try {
            var f = ProdutoRecomendadoService.class.getDeclaredField("produtoRepository");
            f.setAccessible(true);
            f.set(service, produtoRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deveRetornarProdutosRecomendadosPorPerfil() {
        Produto p1 = new Produto("CDB Caixa 12% a.a.", "CDB", 0.12, "Moderado", "CONSERVADOR");
        Produto p2 = new Produto("LCI Caixa 10% a.a.", "LCI", 0.10, "Baixo", "CONSERVADOR");
        p1.id = 1L;
        p2.id = 2L;


        when(produtoRepository.findRecomendadosPorPerfil(eq("CONSERVADOR")))
                .thenReturn(List.of(p1, p2));

        var result = service.recomendar("CONSERVADOR");

        assertEquals(2, result.size());
        assertEquals("CDB Caixa 12% a.a.", result.get(0).getNome());
    }
}