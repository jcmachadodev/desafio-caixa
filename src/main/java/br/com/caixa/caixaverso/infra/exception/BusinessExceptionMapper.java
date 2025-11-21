package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.UUID;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private static final Logger LOG = Logger.getLogger(BusinessExceptionMapper.class);

    @Override
    public Response toResponse(BusinessException e) {
        String errorId = UUID.randomUUID().toString();
        LOG.warnf("errorId=%s business rule violated: %s", errorId, e.getMessage());

        ErrorResponse body = new ErrorResponse(
                errorId,
                422,
                "Unprocessable Entity",
                e.getMessage(),
                Instant.now().toString()
        );

        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
