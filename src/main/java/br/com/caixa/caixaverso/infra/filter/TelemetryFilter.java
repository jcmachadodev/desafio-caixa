package br.com.caixa.caixaverso.infra.filter;

import br.com.caixa.caixaverso.service.TelemetriaService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.USER)
public class TelemetryFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String ATTR_START = "telemetry.start";

    @Inject
    TelemetriaService telemetriaService;

    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.setProperty(ATTR_START, System.currentTimeMillis());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Object startObj = requestContext.getProperty(ATTR_START);
        if (!(startObj instanceof Long)) {
            return;
        }
        long start = (Long) startObj;
        int elapsed = (int) (System.currentTimeMillis() - start);

        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        int status = responseContext.getStatus();


        if (path.startsWith("q/") ||
                path.startsWith("swagger") ||
                path.startsWith("openapi") ||
                path.startsWith("favicon") ||
                path.startsWith("metrics") ||
                path.startsWith("health")) {
            return;
        }


        String serviceName = method + " " + path;

        telemetriaService.recordAsync(serviceName, elapsed, status, path, method);
    }
}
