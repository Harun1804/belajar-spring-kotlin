package com.galaxy.simple_crud.configs

import com.galaxy.simple_crud.services.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val tokenService: JwtService,
    private val jwtDecoder: JwtDecoder
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Configure security settings
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                it.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwtConfig ->
                    jwtConfig.decoder(jwtDecoder) // Use injected JwtDecoder
                }
            }
            .authenticationManager { auth ->
                val jwt = auth as BearerTokenAuthenticationToken
                val user = tokenService.parseToken(jwt.token)
                    ?: throw InvalidBearerTokenException("Invalid token")
                UsernamePasswordAuthenticationToken(user, null, listOf(SimpleGrantedAuthority("USER")))
            }

        // Security headers configuration
        http.headers { headers ->
            headers.frameOptions { it.disable() }
            headers.xssProtection { it.disable() }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}