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
                .antMatchers("/webapi/**").permitAll()
                .antMatchers("/webapi/authentication/**").permitAll()
                .antMatchers("/webapi/courses/**").permitAll()
                .antMatchers("/webapi/subjects/**").permitAll()
                .antMatchers("/webapi/studies/**").permitAll()
                .antMatchers("/webapi/theses/**").permitAll()
                .antMatchers("/webapi/tags/**").permitAll()
                .antMatchers("/webapi/fields-of-study/**").permitAll()
                .antMatchers("/webapi/theses/{thesisID}/comments/**").hasRole("USER")
                .antMatchers("/webapi/theses/comments/**").hasRole("USER")
                .antMatchers("/webapi/theses/comments/**").hasRole("USER")
                .antMatchers("/webapi/users").permitAll()
                .antMatchers("/webapi/users/{userID}").hasRole("USER")
                .antMatchers("/webapi/**").hasRole("ADMIN");

        http.exceptionHandling();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
}
