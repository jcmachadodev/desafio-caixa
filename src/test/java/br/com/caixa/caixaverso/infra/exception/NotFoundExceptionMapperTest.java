package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionMapperTest {

    @Test
    void toResponse_returns404AndBody() {
        NotFoundExceptionMapper mapper = new NotFoundExceptionMapper();
        NotFoundException ex = new NotFoundException("Produto não encontrado: X");

        Response response = mapper.toResponse(ex);

        assertEquals(404, response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof ErrorResponse);

        ErrorResponse body = (ErrorResponse) response.getEntity();
        assertEquals(404, body.getStatus());
        assertEquals("Not Found", body.getError());
        assertEquals("Produto não encontrado: X", body.getMessage());
        assertNotNull(body.getErrorId());
        assertNotNull(body.getTimestamp());
    }
}
