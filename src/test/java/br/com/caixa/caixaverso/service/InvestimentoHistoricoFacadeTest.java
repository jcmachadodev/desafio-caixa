package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.InvestimentoHistoricoDTO;
import br.com.caixa.caixaverso.infra.exception.NotFoundException;
import br.com.caixa.caixaverso.repository.InvestimentoHistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvestimentoHistoricoFacadeTest {

    @Mock
    InvestimentoHistoricoRepository historicoRepository;

    @InjectMocks
    InvestimentoHistoricoService historicoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarPorCliente_comDados_deveRetornarLista() {
        Long clienteId = 1L;
        InvestimentoHistoricoDTO d1 = new InvestimentoHistoricoDTO(1L, "CDB", 5000.0, 0.12, "2025-01-15");
        InvestimentoHistoricoDTO d2 = new InvestimentoHistoricoDTO(2L, "Fundo Multimercado", 3000.0, 0.08, "2025-03-10");

        when(historicoRepository.findByClienteIdAsDTO(clienteId)).thenReturn(List.of(d1, d2));

        List<InvestimentoHistoricoDTO> result = historicoService.listarPorCliente(clienteId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("CDB", result.get(0).getTipo());
        verify(historicoRepository, times(1)).findByClienteIdAsDTO(clienteId);
    }

    @Test
    public void listarPorCliente_semDados_deveLancarNotFound() {
        Long clienteId = 99L;
        when(historicoRepository.findByClienteIdAsDTO(clienteId)).thenReturn(List.of());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> historicoService.listarPorCliente(clienteId));
        assertTrue(ex.getMessage().contains("Nenhum investimento encontrado"));
    }

    @Test
    public void listarPorCliente_clienteIdNulo_deveLancarIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> historicoService.listarPorCliente(null));
    }
}
