package ch.fwesterath.logisticsapi.auth.apikey;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

public class ApiKeyFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(ApiKeyFilter.class);
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String remoteAddr = httpRequest.getHeader("X-Forwarded-For");
        if (remoteAddr == null) {
            remoteAddr = httpRequest.getRemoteAddr();
        }

        logger.info(httpRequest.getMethod() + " requestURI=" + requestURI + "; IP address: " + remoteAddr);
        // check if the requested URL matches any excluded URLs
        if (requestURI.startsWith("/") || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }
        String apiKey = httpRequest.getHeader("API_KEY");
        if (apiKey != null && apiKey.trim().length() > 0) {
            Optional<ApiKey> optionalApiKey = apiKeyRepository.findByKeyAndActive(apiKey, true);
            if (optionalApiKey.isPresent()) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        // Log the request URI and the IP address of the client that sent the request
    }
}
