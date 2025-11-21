package br.com.caixa.caixaverso.infra.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.UUID;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable e) {
        String errorId = UUID.randomUUID().toString();
        LOG.errorf(e, "errorId=%s unexpected error: %s", errorId, e.getMessage());

        ErrorResponse body = new ErrorResponse(
                errorId,
                500,
                "Internal Server Error",
                "Ocorreu um erro inesperado. Informe o errorId ao suporte.",
                Instant.now().toString()
        );

        return Response.status(500)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
