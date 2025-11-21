package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.UUID;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger LOG = Logger.getLogger(NotFoundExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundException e) {
        String errorId = UUID.randomUUID().toString();
        LOG.warnf("errorId=%s not found: %s", errorId, e.getMessage());

        ErrorResponse body = new ErrorResponse(
                errorId,
                404,
                "Not Found",
                e.getMessage(),
                Instant.now().toString()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
