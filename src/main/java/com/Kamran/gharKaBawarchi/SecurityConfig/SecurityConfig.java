package com.Kamran.gharKaBawarchi.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter JwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csfr -> csfr.disable())
            //configuration end point authorization
            .authorizeHttpRequests( auth -> auth
                                    //public end point
                                    .requestMatchers("/login","/cook/login","/register","/cook/register","/css/**", "/js/**").permitAll()
                                    //role bases end point
                                    .requestMatchers("/home/**").hasRole("USER")
                                    .requestMatchers("/cook/home/**").hasRole("COOK")
                                    //all other required authentication
                                    .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //set custome authentication provider
            //.authenticationProvider(authenticationProvider())
            //add jwt filter before spring Security default filter
            .addFilterBefore(JwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout.disable());
            return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager for controllers to authenticate username/password
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        return (request, response, exception) -> {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/login").forward(request, response);
        };
    }


}
