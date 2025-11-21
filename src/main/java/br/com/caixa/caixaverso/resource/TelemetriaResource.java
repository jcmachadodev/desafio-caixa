package br.com.caixa.caixaverso.resource;

import br.com.caixa.caixaverso.domain.dto.PeriodoDTO;
import br.com.caixa.caixaverso.domain.dto.TelemetriaDTO;
import br.com.caixa.caixaverso.domain.dto.TelemetriaReportDTO;
import br.com.caixa.caixaverso.infra.exception.ErrorResponse;
import br.com.caixa.caixaverso.service.TelemetriaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Telemetria", description = "Métricas e estatísticas da API")
@SecurityRequirement(name = "keycloak-oauth")
public class TelemetriaResource {

    @Inject
    TelemetriaService telemetriaService;

    @GET
    @Operation(summary = "Retorna dados de telemetria (agregados) para um período",
            description = "Parâmetros opcionais. Formato ISO 8601 YYYY-MM-DD. Se ausentes, padrão últimos 30 dias.")
    @APIResponse(responseCode = "200", description = "Relatório de telemetria",
            content = @Content(schema = @Schema(implementation = TelemetriaReportDTO.class)))
    @APIResponse(responseCode = "400", description = "Parâmetros inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public TelemetriaReportDTO listar( @Parameter(description = "Data inicial (YYYY-MM-DD)", example = "2025-11-01")
                                       @QueryParam("inicio") String inicio,
                                       @Parameter(description = "Data final (YYYY-MM-DD)", example = "2025-11-30")
                                      @QueryParam("fim") String fim) {

        List<TelemetriaDTO> items = telemetriaService.getAggregatedByService(inicio, fim);
        TelemetriaService.PeriodRange pr = telemetriaService.computePeriodRange(inicio, fim);

        TelemetriaReportDTO report = new TelemetriaReportDTO();
        report.setServicos(items);
        report.setPeriodo(new PeriodoDTO(pr.start.toString(), pr.end.toString()));
        return report;
    }
}
