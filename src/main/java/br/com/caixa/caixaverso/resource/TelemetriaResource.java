package br.com.caixa.caixaverso.resource;

import br.com.caixa.caixaverso.domain.dto.PeriodoDTO;
import br.com.caixa.caixaverso.domain.dto.TelemetriaDTO;
import br.com.caixa.caixaverso.domain.dto.TelemetriaReportDTO;
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
    @Operation(summary = "Retorna dados de telemetria da aplicação")
    @APIResponse(
            responseCode = "200",
            description = "Relatório de telemetria",
            content = @Content(schema = @Schema(implementation = TelemetriaReportDTO.class))
    )
    public TelemetriaReportDTO listar(@QueryParam("inicio") String inicio,
                                      @QueryParam("fim") String fim) {

        List<TelemetriaDTO> items = telemetriaService.getAggregatedByService(inicio, fim);
        TelemetriaService.PeriodRange pr = telemetriaService.computePeriodRange(inicio, fim);

        TelemetriaReportDTO report = new TelemetriaReportDTO();
        report.setServicos(items);
        report.setPeriodo(new PeriodoDTO(pr.start.toString(), pr.end.toString()));
        return report;
    }
}
