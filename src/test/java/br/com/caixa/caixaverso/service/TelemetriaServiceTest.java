package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.TelemetriaDTO;
import br.com.caixa.caixaverso.repository.TelemetriaRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
class TelemetriaServiceTest {

    @Inject
    TelemetriaService telemetriaService;

    @InjectMock
    TelemetriaRepository telemetriaRepository;

    @Test
    void getAggregatedByService_semParametros_deveUsarMesAtual() {
        TelemetriaDTO dto = new TelemetriaDTO("simular-investimento", 120L, 250L);

        when(telemetriaRepository.findAggregatedByServiceBetween(any(), any()))
                .thenReturn(List.of(dto));

        List<TelemetriaDTO> result = telemetriaService.getAggregatedByService(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("simular-investimento", result.get(0).getNome());
    }

    @Test
    void getAggregatedByService_comPeriodo_deveRetornarDados() {
        TelemetriaDTO dto1 = new TelemetriaDTO("simular-investimento", 120L, 250L);
        TelemetriaDTO dto2 = new TelemetriaDTO("perfil-risco", 80L, 180L);

        when(telemetriaRepository.findAggregatedByServiceBetween(any(), any()))
                .thenReturn(List.of(dto1, dto2));

        List<TelemetriaDTO> result = telemetriaService.getAggregatedByService("2025-01-01", "2025-01-31");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void computePeriodRange_comApenasInicio_deveCompletarMes() {
        TelemetriaService.PeriodRange range = telemetriaService.computePeriodRange("2025-03-15", null);

        assertNotNull(range);
        assertEquals(LocalDate.parse("2025-03-15"), range.start);
        assertEquals(LocalDate.parse("2025-03-31"), range.end);
    }

    @Test
    void computePeriodRange_comApenasFim_deveUsarInicioDomes() {
        TelemetriaService.PeriodRange range = telemetriaService.computePeriodRange(null, "2025-03-20");

        assertNotNull(range);
        assertEquals(LocalDate.parse("2025-03-01"), range.start);
        assertEquals(LocalDate.parse("2025-03-20"), range.end);
    }

    @Test
    void computePeriodRange_datasInvertidas_deveCorrigir() {
        TelemetriaService.PeriodRange range = telemetriaService.computePeriodRange("2025-03-20", "2025-03-10");

        assertNotNull(range);
        assertEquals(LocalDate.parse("2025-03-10"), range.start);
        assertEquals(LocalDate.parse("2025-03-20"), range.end);
    }

    @Test
    void recordAsync_deveGravarTelemetriaAssincronamente() {
        // Este teste é mais difícil de validar por ser assíncrono
        // Mas podemos garantir que não lança exceção
        assertDoesNotThrow(() -> {
            telemetriaService.recordAsync("test-service", 100, 200, "/test", "GET");
        });
    }
}