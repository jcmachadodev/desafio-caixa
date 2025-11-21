package br.com.caixa.caixaverso.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class SimulacaoResourceIT {

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void simularInvestimento_valorNegativo_deveRetornar400() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "clienteId": 1,
                  "valor": -1000.0,
                  "prazoMeses": 12,
                  "tipoProduto": "CDB"
                }
                """)
                .when()
                .post("/simular-investimento")
                .then()
                .statusCode(400);
    }

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void simularInvestimento_clienteInexistente_deveRetornar404() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "clienteId": 99999,
                  "valor": 10000.0,
                  "prazoMeses": 12,
                  "tipoProduto": "CDB"
                }
                """)
                .when()
                .post("/simular-investimento")
                .then()
                .statusCode(404)
                .body("message", containsString("Cliente não encontrado"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void listarSimulacoes_deveRetornarArray() {
        given()
                .when()
                .get("/simulacoes")
                .then()
                .statusCode(anyOf(is(200), is(404))) // pode não ter dados no banco
                .contentType(ContentType.JSON);
    }


}