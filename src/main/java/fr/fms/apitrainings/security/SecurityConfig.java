package fr.fms.apitrainings.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    String usersByUsernameQuery = "select email, password, enable from users where email = ?";
    String authoritiesByUsernameQuery = "SELECT u.email, r.name from users AS u \r\n"
            + "INNER JOIN users_roles ur ON u.id = ur.users_id \r\n"
            + "INNER JOIN role r ON ur.roles_id = r.id \r\n"
            + "where email = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(usersByUsernameQuery)
                .authoritiesByUsernameQuery(authoritiesByUsernameQuery).rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .antMatchers(HttpMethod.GET, "/api/category/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/order").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/api/orders").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/orderItems/{orderId}").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/order/{orderId}").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/trainings").permitAll()
                .antMatchers(HttpMethod.POST, "/api/trainings").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/training/{id}").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/trainings/{id}").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/training/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/categorie/{id}/trainings").permitAll()
                .antMatchers(HttpMethod.GET, "/api/trainingImage/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/uploadImage").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
