package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static io.quarkus.arc.ComponentsProvider.LOG;

@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException e) {

        String errorId = UUID.randomUUID().toString();
        LOG.errorf(e, "errorId=%s unexpected error: %s", errorId, e.getMessage());

        ErrorResponse body = new ErrorResponse(
                errorId,
                400,
                "Bad Request",
                e.getMessage(),
                Instant.now().toString()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}

