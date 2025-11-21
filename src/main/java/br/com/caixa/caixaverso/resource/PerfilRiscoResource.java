package br.com.caixa.caixaverso.resource;

import br.com.caixa.caixaverso.service.PerfilRiscoFacade;
import br.com.caixa.caixaverso.domain.dto.PerfilRiscoDTO;

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

@RequestScoped
@Path("/perfil-risco")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Perfil de Risco")
@SecurityRequirement(name = "keycloak-oauth")
public class PerfilRiscoResource {

    @Inject
    PerfilRiscoFacade PerfilRiscoFacade;

    @GET
    @Path("{clienteId}")
    @Operation(
            summary = "Retorna o perfil de risco do cliente",
            description = "Calcula o perfil (Conservador, Moderado, Agressivo) com pontuação e justificativas."
    )
    @APIResponse(
            responseCode = "200",
            description = "Perfil de risco calculado",
            content = @Content(schema = @Schema(implementation = PerfilRiscoDTO.class))
    )
    public Response perfilPorCliente(@PathParam("clienteId") Long clienteId) {
        PerfilRiscoDTO dto = PerfilRiscoFacade.calcularPerfil(clienteId);
        return Response.ok(dto).build();
    }
}
