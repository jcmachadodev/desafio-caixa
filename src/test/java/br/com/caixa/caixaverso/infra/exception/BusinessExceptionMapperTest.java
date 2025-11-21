package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionMapperTest {

    @Test
    void toResponse_returns422AndBody() {
        BusinessExceptionMapper mapper = new BusinessExceptionMapper();
        BusinessException ex = new BusinessException("Tipo de produto inválido: xCDB");

        Response response = mapper.toResponse(ex);

        assertEquals(422, response.getStatus());
        assertNotNull(response.getEntity());
        assertTrue(response.getEntity() instanceof ErrorResponse);

        ErrorResponse body = (ErrorResponse) response.getEntity();
        assertEquals(422, body.getStatus());
        assertEquals("Unprocessable Entity", body.getError());
        assertEquals("Tipo de produto inválido: xCDB", body.getMessage());
        assertNotNull(body.getErrorId());
        assertNotNull(body.getTimestamp());
    }
}
