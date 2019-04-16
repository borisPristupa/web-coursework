package com.ifmo.web.coursework.config;

import com.ifmo.web.coursework.webservices.handler.BadCredentialsHandler;
import com.ifmo.web.coursework.webservices.handler.PermissionDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                    .antMatchers("/sign/**", "/forbidden", "/time").permitAll()
                    .antMatchers("/artifact/privileged/approve").hasRole("RESEARCHER")
                    .antMatchers("/human/privileged", "/artifact/privileged/**", "/expedition/privileged/ban")
                        .hasRole("MODERATOR")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/sign/in")
                    .successForwardUrl("/sign/in/success")
                    .failureHandler(new BadCredentialsHandler())
                .and().logout()
                    .logoutUrl("/sign/out")
                    .logoutSuccessUrl("/sign/out/success");
        http.exceptionHandling().authenticationEntryPoint(new PermissionDeniedHandler());
        http.exceptionHandling().accessDeniedHandler(new PermissionDeniedHandler());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
