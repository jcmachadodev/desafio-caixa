package br.com.caixa.caixaverso;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.OAuthScope;

@OpenAPIDefinition(
        info = @Info(
                title = "Simulador de Investimentos",
                version = "1.0.0",
                contact = @Contact(name = "Desafio Caixaverso", email = "teste@caixa.gov.br"),
                description = "API para simulações de investimento, relatórios, telemetria e recomendação."
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        },
        security = @SecurityRequirement(name = "keycloak-oauth")
)

@SecurityScheme(
        securitySchemeName = "keycloak-oauth",
        type = SecuritySchemeType.OAUTH2,
        description = "Autenticação via Keycloak",
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:9090/realms/desafio-caixaverso/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:9090/realms/desafio-caixaverso/protocol/openid-connect/token",
                        scopes = @OAuthScope(name = "openid", description = "Escopo padrão OIDC")
                )
        )
)
public class ApiApplication extends Application {

}