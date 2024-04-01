package com.backend.core.infrastructure.config.security;

import com.backend.core.entity.api.ApiResponse;
import com.backend.core.usecase.statics.ErrorTypeEnum;
import com.backend.core.usecase.util.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class WebSecurityConfig {
    final JwtAuthenticationFilter jwtAuthenticationFilter;
    final AuthenticationProvider authenticationProvider;
    final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers("/unauthen/**", "/error", "/wsNotificationEndPoint/**").permitAll();
                            request.anyRequest().authenticated();
                        }
                )
                .sessionManagement(
                        sessionManagement -> {
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        }
                )
                .userDetailsService(userDetailsService)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exception -> {
                            exception.authenticationEntryPoint(
                                    (request, response, authException) -> {
                                        ApiResponse apiResponse = new ApiResponse("failed", ErrorTypeEnum.UNAUTHORIZED.name());

                                        String jsonErrorResponse = new ObjectMapper().writeValueAsString(apiResponse);

                                        response.setContentType("application/json");
                                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                        response.getWriter().write(jsonErrorResponse);
                                    }
                            );
                        }
                );

        return httpSecurity.build();
    }


    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setDefaultRolePrefix("");

        return handler;
    }

    @Bean
    public CookieSameSiteSupplier cookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofNone();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(ImmutableList.of("*"));
        config.setAllowCredentials(true);
        config.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
