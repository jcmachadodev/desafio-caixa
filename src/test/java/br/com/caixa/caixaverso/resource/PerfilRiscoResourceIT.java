package br.com.caixa.caixaverso.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PerfilRiscoResourceIT {

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void perfilPorCliente_clienteComHistorico_deveRetornarPerfil() {
        given()
                .when()
                .get("/perfil-risco/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("clienteId", equalTo(1))
                .body("perfil", notNullValue())
                .body("perfil", anyOf(equalTo("CONSERVADOR"), equalTo("MODERADO"), equalTo("AGRESSIVO")))
                .body("pontuacao", notNullValue())
                .body("pontuacao", allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(100)))
                .body("descricao", notNullValue());
    }



    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void perfilPorCliente_idInvalido_deveRetornar200ComPerfilPadrao() {
        given()
                .when()
                .get("/perfil-risco/99999")
                .then()
                .statusCode(200)
                .body("clienteId", equalTo(99999))
                .body("perfil", notNullValue());
    }
}