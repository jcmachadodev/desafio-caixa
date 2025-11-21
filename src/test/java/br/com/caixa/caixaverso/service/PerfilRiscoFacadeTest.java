package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.PerfilRiscoDTO;
import br.com.caixa.caixaverso.domain.entity.Cliente;
import br.com.caixa.caixaverso.repository.InvestimentoHistoricoRepository;
import br.com.caixa.caixaverso.repository.ProdutoRepository;
import br.com.caixa.caixaverso.repository.ClienteRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
public class PerfilRiscoFacadeTest {

    @Inject
    PerfilRiscoFacade perfilRiscoService;

    @InjectMock
    InvestimentoHistoricoRepository historicoRepo;

    @InjectMock
    ClienteRepository clienteRepository;

    @InjectMock
    ProdutoRepository produtoRepository;


    private List<Object[]> makeDailyTotals(double... totals) {
        List<Object[]> daily = new ArrayList<>();
        for (int i = 0; i < totals.length; i++) {
            daily.add(new Object[]{ String.format("2025-03-%02d", i + 1), totals[i] });
        }
        return daily;
    }



    @Test
    public void calcularPerfil_semHistorico_deveUsarPontuacaoCliente() {
        Long clienteId = 10L;


        when(historicoRepo.countOpsAll(clienteId)).thenReturn(0L);
        when(historicoRepo.sumVolumeAll(clienteId)).thenReturn(0.0);
        when(historicoRepo.dailyTotalsAll(clienteId)).thenReturn(new ArrayList<>());
        when(historicoRepo.countDistinctTypesAll(clienteId)).thenReturn(0);
        when(historicoRepo.volumeByTypeAll(clienteId)).thenReturn(new HashMap<>());


        Cliente c = new Cliente();
        c.id = clienteId;
        c.nome = "Teste";
        c.pontuacao = 55;
        when(clienteRepository.findByIdOrNull(clienteId)).thenReturn(c);

        PerfilRiscoDTO dto = perfilRiscoService.calcularPerfil(clienteId);

        assertNotNull(dto);
        assertEquals(clienteId, dto.getClienteId());
        assertEquals(55, dto.getPontuacao());
        assertEquals("MODERADO", dto.getPerfil());
    }

    @Test
    public void calcularPerfil_baixoVolumeBaixaFrequencia_deveSerConservador() {
        Long clienteId = 30L;


        when(historicoRepo.countOpsAll(clienteId)).thenReturn(3L);
        when(historicoRepo.sumVolumeAll(clienteId)).thenReturn(2_000.0);


        when(historicoRepo.dailyTotalsAll(clienteId)).thenReturn(makeDailyTotals(2000, 2100, 2050));


        when(historicoRepo.countDistinctTypesAll(clienteId)).thenReturn(1);
        Map<String, Double> volumeByType = new HashMap<>();
        volumeByType.put("LCI", 2000.0);
        when(historicoRepo.volumeByTypeAll(clienteId)).thenReturn(volumeByType);

        when(clienteRepository.findByIdOrNull(clienteId)).thenReturn(null);

        PerfilRiscoDTO dto = perfilRiscoService.calcularPerfil(clienteId);

        assertNotNull(dto);
        assertEquals(clienteId, dto.getClienteId());
        assertEquals("CONSERVADOR", dto.getPerfil());
        assertTrue(dto.getPontuacao() < 40, "Pontuação deve ser baixa para perfil conservador");
    }
}
