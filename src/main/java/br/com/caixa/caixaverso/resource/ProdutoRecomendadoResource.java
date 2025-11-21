package br.com.caixa.caixaverso.resource;

import br.com.caixa.caixaverso.domain.dto.ProdutoDTO;
import br.com.caixa.caixaverso.domain.dto.ProdutoValidadoDTO;
import br.com.caixa.caixaverso.service.ProdutoRecomendadoService;

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
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@RequestScoped
@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "keycloak-oauth")
@Tag(name = "Produtos Recomendados", description = "Endpoints para obter produtos recomendados por perfil")
public class ProdutoRecomendadoResource {

    @Inject
    ProdutoRecomendadoService service;

    @GET
    @Path("{perfil}")
    @Operation(
            summary = "Lista produtos recomendados por perfil",
            description = "Retorna uma lista de produtos adequados ao perfil informado (Conservador / Moderado / Agressivo)."
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de produtos recomendados",
            content = @Content(schema = @Schema(implementation = ProdutoValidadoDTO.class))
    )
    @APIResponse(responseCode = "400", description = "Parâmetro inválido")
    public Response recomendar(@Parameter(description = "Perfil desejado: Conservador | Moderado | Agressivo", example = "Moderado")
                                   @PathParam("perfil") String perfil) {
        List<ProdutoValidadoDTO> recomendados = service.recomendar(perfil);
        return Response.ok(recomendados).build();
    }
}
