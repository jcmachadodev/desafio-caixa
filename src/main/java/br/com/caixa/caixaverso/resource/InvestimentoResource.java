package br.com.caixa.caixaverso.resource;

import br.com.caixa.caixaverso.domain.dto.InvestimentoHistoricoDTO;
import br.com.caixa.caixaverso.service.InvestimentoHistoricoService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@RequestScoped
@Path("/investimentos")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "keycloak-oauth")
@Tag(name = "Investimentos", description = "Consulta histórico de investimentos do cliente")
public class InvestimentoResource {

    @Inject
    InvestimentoHistoricoService historicoService;

    @GET
    @Path("{clienteId}")
    @Operation(
            summary = "Retorna histórico de investimentos do cliente",
            description = "Busca todos os investimentos registrados para o cliente informado."
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de investimentos",
            content = @Content(schema = @Schema(implementation = InvestimentoHistoricoDTO.class))
    )
    @APIResponse(responseCode = "404", description = "Nenhum investimento encontrado para o cliente")
    public Response listarPorCliente(@PathParam("clienteId") Long clienteId) {
        List<InvestimentoHistoricoDTO> list = historicoService.listarPorCliente(clienteId);
        return Response.ok(list).build();
    }
}
