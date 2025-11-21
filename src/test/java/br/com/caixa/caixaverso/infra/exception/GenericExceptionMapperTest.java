package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericExceptionMapperTest {

    @Test
    void toResponse_returns500AndGenericMessage() {
        GenericExceptionMapper mapper = new GenericExceptionMapper();
        RuntimeException ex = new RuntimeException("boom");

        Response response = mapper.toResponse(ex);

        assertEquals(500, response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof ErrorResponse);

        ErrorResponse body = (ErrorResponse) response.getEntity();
        assertEquals(500, body.getStatus());
        assertEquals("Internal Server Error", body.getError());
        assertEquals("Ocorreu um erro inesperado. Informe o errorId ao suporte.", body.getMessage());
        assertNotNull(body.getErrorId());
        assertNotNull(body.getTimestamp());
    }
}
