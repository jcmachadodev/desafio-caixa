package br.com.caixa.caixaverso.resource;

import br.com.caixa.caixaverso.domain.dto.ProdutoPorDiaDTO;
import br.com.caixa.caixaverso.domain.dto.SimulacaoListDTO;
import br.com.caixa.caixaverso.domain.dto.SimulacaoRequestDTO;
import br.com.caixa.caixaverso.domain.dto.SimulacaoResponseDTO;
import br.com.caixa.caixaverso.service.SimulationFacade;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@RequestScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Simulação", description = "Operações de simulação de investimentos")
@SecurityRequirement(name = "keycloak-oauth")
public class SimulacaoResource {

    @Inject
    SimulationFacade simulationFacade;

    @POST
    @Path("simular-investimento")
    @Operation(
            summary = "Simula um investimento",
            description = "Recebe os dados da simulação, valida, escolhe o produto adequado e retorna o resultado."
    )
    @APIResponse(
            responseCode = "200",
            description = "Simulação realizada com sucesso",
            content = @Content(
                    schema = @Schema(implementation = SimulacaoResponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Exemplo",
                                    value = """
                    {
                      "produtoValidado": {
                        "id": 101,
                        "nome": "CDB Caixa 2026",
                        "tipo": "CDB",
                        "rentabilidade": 0.12,
                        "risco": "Baixo"
                      },
                      "resultadoSimulacao": {
                        "valorFinal": 11200.00,
                        "rentabilidadeEfetiva": 0.12,
                        "prazoMeses": 12
                      },
                      "dataSimulacao": "2025-10-31T14:00:00Z"
                    }
                    """
                            )
                    }
            )
    )
    public Response simularInvestimento(
            @RequestBody(
                    required = true,
                    description = "Dados da simulação",
                    content = @Content(
                            schema = @Schema(implementation = SimulacaoRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo",
                                    value = """
                    {
                      "clienteId": 1,
                      "valor": 10000.00,
                      "prazoMeses": 12,
                      "tipoProduto": "CDB"
                    }
                    """
                            )
                    )
            )
            @Valid SimulacaoRequestDTO request) {
        SimulacaoResponseDTO response = simulationFacade.simular(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("simulacoes")
    @Operation(summary = "Lista todas as simulações realizadas")
    @APIResponse(
            responseCode = "200",
            description = "Lista de simulações",
            content = @Content(
                    schema = @Schema(implementation = SimulacaoListDTO.class)
            )
    )
    public Response listarSimulacoes() {
        List<SimulacaoListDTO> list = simulationFacade.listarSimulacoes();
        return Response.ok(list).build();
    }

    @GET
    @Path("simulacoes/por-produto-dia")
    @Operation(
            summary = "Retorna agregação de simulações por produto e dia",
            description = "Agrupa a quantidade de simulações e valor médio final por produto para cada dia."
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista agregada por produto e dia",
            content = @Content(schema = @Schema(implementation = ProdutoPorDiaDTO.class))
    )
    public Response simulacoesPorProdutoDia() {
        List<ProdutoPorDiaDTO> result = simulationFacade.listarSimulacoesPorProdutoDia();
        return Response.ok(result).build();
    }
}
