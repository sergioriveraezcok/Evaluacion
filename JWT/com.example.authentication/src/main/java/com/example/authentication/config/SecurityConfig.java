package com.example.authentication.config;

import com.example.authentication.service.UserService;
import com.example.authentication.util.JwtAuthenticationFilter;
import com.example.authentication.util.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuración de seguridad para la aplicación.
 */
@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * @param http Configuración HTTP.
     * @param userDetailsService Servicio para cargar usuarios.
     * @return Cadena de filtros de seguridad configurada.
     * @throws Exception posibles excepciones durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para facilitar pruebas.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll() // Rutas públicas.
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("ADMIN") // Rutas protegidas para ADMIN.
                        .requestMatchers(new AntPathRequestMatcher("/api/user/**")).hasAnyRole("USER", "ADMIN") // Rutas protegidas para USER y ADMIN.
                        .anyRequest().authenticated() // Requiere autenticación para todas las demás rutas.
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class) // Agrega filtro de autenticación JWT.
                .build();
    }

    /**
     * Configura el AuthenticationManager utilizando UserDetailsService.
     * @param configuration Configuración de autenticación.
     * @return AuthenticationManager configurado.
     * @throws Exception posibles excepciones durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configura el UserDetailsService para cargar usuarios desde UserService.
     * @param userService Servicio de usuarios.
     * @return UserDetailsService configurado.
     */
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
