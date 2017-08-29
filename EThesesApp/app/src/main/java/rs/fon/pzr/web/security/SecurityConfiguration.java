package rs.fon.pzr.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import rs.fon.pzr.web.security.filter.TicketProccessingFilter;

@Configuration
@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TicketProccessingFilter jwtTokenFilter;

    @Autowired
    public SecurityConfiguration(TicketProccessingFilter ticketProccessingFilter) {
        this.jwtTokenFilter = ticketProccessingFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/subjects/**").permitAll()
                .antMatchers("/studies/**").permitAll()
                .antMatchers("/theses/**").permitAll()
                .antMatchers("/tags/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/courses/**").permitAll()
                .antMatchers("/studies/**").hasRole("ADMIN")
                .antMatchers("/fields-of-study/**").permitAll()
                .antMatchers("/theses/{thesisID}/comments/**").authenticated()
                .antMatchers("/theses/comments/**").hasRole("USER")
                .antMatchers("/theses/comments/**").hasRole("USER")
                .antMatchers("/users/{userID}").hasRole("USER")
                .antMatchers("/**").permitAll();

        http.exceptionHandling();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
}
