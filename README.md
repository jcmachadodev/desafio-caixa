# Simulador de Investimentos – Desafio Caixaverso
Back-end Java utilizando Quarkus, Keycloak e SQLite

## 1. Introdução

Este documento apresenta a solução desenvolvida para o Desafio Caixaverso – Back-end Java. A aplicação implementa uma API completa para simulação de investimentos, cálculo de perfil de risco, recomendação de produtos financeiros, telemetria e consulta de histórico.  
O sistema foi construído utilizando Quarkus 3 e Java 21, com autenticação via Keycloak e persistência local em SQLite.

O objetivo é fornecer uma infraestrutura leve, funcional, reproduzível e alinhada ao enunciado do desafio.

---

## 2. Arquitetura Geral

A arquitetura segue uma estrutura modular e organizada, dividida em camadas:

```
Resource (API REST)
    ↓
Facade (Orquestração de casos de uso)
    ↓
Service (Regras de negócio)
    ↓
Strategy (Simulações por tipo de produto)
    ↓
Repository (Acesso ao banco via Panache + SQLite)
```

Componentes adicionais complementam a solução:
- DTOs para padronização de entrada e saída
- Exception Mappers para respostas consistentes
- Camada de telemetria aplicada aos serviços
- Configuração OIDC utilizando Keycloak
- Dockerfile e Docker Compose para ambiente completo

---

## 3. Tecnologias Utilizadas

- Java 21
- Quarkus 3.29.x
- Keycloak 23.0.7
- SQLite
- Swagger / SmallRye OpenAPI
- Maven
- Docker e Docker Compose
- JUnit 5 / Quarkus Test

---

## 4. Como Executar

### 4.1 Execução via Docker Compose

Esta é a forma oficial de execução do projeto, garantindo ambiente idêntico ao esperado no desafio.

Pré-requisitos:
- Docker
- Docker Compose

Procedimento:
1. Na raiz do projeto, execute:
```
docker compose up -d --build
```

1.1. Para parar o projeto
```
docker compose down -v
```

1.2. para ver o log 
```
docker compose logs -f app
```

2. Serviços disponíveis após inicialização:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/q/swagger-ui
- Keycloak: http://localhost:9090

3. O Keycloak carrega automaticamente o realm localizado em:
```
keycloak-config/realm-export.json
```
---

## 5. Autenticação – Keycloak

A API utiliza Keycloak em modo **bearer-only**, validando tokens obtidos externamente.

Configurações principais:
- Realm: `desafio-caixaverso`
- Client: `desafio-api`
- Usuário para testes: `c123456` / `c123456`

Para gerar um token:
```
curl -X POST "http://localhost:9090/realms/desafio-caixaverso/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=desafio-api" \
  -d "username=c123456" \
  -d "password=c123456"
```

---

## 6. Documentação da API

A documentação OpenAPI/Swagger está disponível em:
```
http://localhost:8080/q/swagger-ui
```

Endpoints implementados de acordo com o desafio:
- POST /simular-investimento
- GET /simulacoes
- GET /simulacoes/por-produto-dia
- GET /perfil-risco/{clienteId}
- GET /produtos-recomendados/{perfil}
- GET /investimentos/{clienteId}
- GET /telemetria

---

## 7. Estrutura do Projeto

```
src/
 ├── main/
 │   ├── docker/
 │   ├── java/br/com/caixa/caixaverso/
 │   │   ├── domain/
 │   │   │   ├── dto/
 │   │   │   └── entity/
 │   │   ├── infra/
 │   │   │   ├── exception/
 │   │   │   └── filter/
 │   │   ├── mapper/
 │   │   ├── repository/
 │   │   ├── resource/
 │   │   └── service/
 │   │       ├── factory/
 │   │       └── strategy/
 │   └── resources/
 │       └── META-INF/
 └── test/
     └── java/br/com/caixa/caixaverso/
         ├── infra/exception/
         └── service/
```

---

## 8. Motor de Recomendação

O motor de recomendação determina o perfil de risco do cliente com base em três fatores:

1. Volume total investido
2. Frequência das operações
3. Rentabilidade média

Para volume e frequência, aplica-se **normalização logarítmica**, utilizando:

- `Math.log10(1 + valor)`
- multiplicadores configuráveis
- limite de 0 a 100

A pontuação final é uma combinação ponderada das métricas normalizadas.

Classificação:
- Conservador
- Moderado
- Agressivo

A recomendação de produtos utiliza este perfil para selecionar os investimentos mais adequados.

---

## 9. Telemetria

O sistema registra:
- quantidade de chamadas por serviço
- tempos médios e máximos
- agregação por data

Endpoint:
```
GET /telemetria
```

---

## 10. Testes Automatizados

Testes incluídos:
- estratégias de simulação
- cálculo de perfil
- recomendação de produtos
- validação de exceções
- serviços e regras de negócio

Para executar:
```
mvn test
```

---

## 11. Decisões de Arquitetura

- Adoção do Quarkus para inicialização rápida e baixo consumo
- Panache ORM para produtividade no acesso ao banco
- Strategy Pattern para simulação por tipo de produto
- Facade para orquestração de casos de uso
- Keycloak para autenticação padrão do mercado
- Docker Compose para reprodutibilidade
- Normalização logarítmica para equilibrar o impacto dos dados

---

## 12. Evidências de Entrega

- Simulação de produtos financeiros
- Histórico e agregação diária
- Telemetria funcional
- Perfil de risco dinâmico
- Recomendação adequada ao perfil
- API autenticada via Keycloak
- Dockerfile e docker-compose
- Testes unitários
- Documentação Swagger
- Código organizado e modular

---

# Fim do Documento
