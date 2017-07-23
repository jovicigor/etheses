package rs.fon.pzr.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import rs.fon.pzr.core.service.authentication.AuthenticationService;

@Component
public class TicketProccessingFilter extends GenericFilterBean {

    private static final Logger logger = Logger.getLogger(TicketProccessingFilter.class);

    private final UserDetailsService customUserDetailsService;
    private final AuthenticationService authenticationService;

    @Autowired
    public TicketProccessingFilter(AuthenticationService authenticationService, UserDetailsService customUserDetailsService, AuthenticationManager authenticationManager) {
        super();
        this.authenticationService = authenticationService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.debug("###############TicketProcessingFilter################");
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            //CHECK IF VALID TICKET EXSIST AND IF SO SET AUTHENTICATION
            String ticket = httpRequest.getHeader("ticket");
            logger.debug("Ticket: " + ticket);
            try {
                String userName = authenticationService.validate(ticket);
                logger.debug("UserEmail: " + userName);
                UserDetails user = customUserDetailsService
                        .loadUserByUsername(userName);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
