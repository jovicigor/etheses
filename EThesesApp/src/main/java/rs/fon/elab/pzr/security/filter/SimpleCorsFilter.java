package rs.fon.elab.pzr.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

class SimpleCorsFilter extends OncePerRequestFilter {
    private final Logger logger = Logger.getLogger(SimpleCorsFilter.class);

    private SimpleCorsFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        logger.debug("SimpleCORSFilter");
        if (request.getHeader("Access-Control-Request-Method") != null
                && "OPTIONS".equals(request.getMethod())) {
            logger.debug("Sending Header to enable CrosOrigin requests...");
            response.addHeader("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Max-Age", "3600");
            response.addHeader("Access-Control-Allow-Headers",
                    "Content-Type, accept, ticket");
        }

        filterChain.doFilter(request, response);
    }

}
