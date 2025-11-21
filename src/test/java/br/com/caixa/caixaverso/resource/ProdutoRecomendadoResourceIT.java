package br.com.caixa.caixaverso.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ProdutoRecomendadoResourceIT {

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void recomendar_perfilConservador_deveRetornarProdutosBaixoRisco() {
        given()
                .when()
                .get("/produtos-recomendados/CONSERVADOR")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()))
                .body("[0].nome", notNullValue())
                .body("[0].tipo", notNullValue())
                .body("[0].rentabilidade", notNullValue())
                .body("[0].risco", notNullValue());
    }

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void recomendar_perfilModerado_deveRetornarProdutos() {
        given()
                .when()
                .get("/produtos-recomendados/MODERADO")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()));
    }

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void recomendar_perfilAgressivo_deveRetornarProdutosAltoRisco() {
        given()
                .when()
                .get("/produtos-recomendados/AGRESSIVO")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()));
    }

    @Test
    @TestSecurity(user = "testUser", roles = "user")
    void recomendar_perfilCaseInsensitive_deveFuncionar() {
        given()
                .when()
                .get("/produtos-recomendados/conservador")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }



}