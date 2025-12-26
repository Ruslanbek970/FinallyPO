package kz.narxoz.hotelbooking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ✅ Postman JSON => CSRF OFF
                .csrf(csrf -> csrf.disable())

                // ✅ Сессии включены (по умолчанию)
                .sessionManagement(session -> session.sessionFixation().migrateSession())

                .authorizeHttpRequests(auth -> auth
                        // ✅ OPEN
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/logout").permitAll()

                        // ✅ ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ✅ MANAGER
                        .requestMatchers("/api/manager/**").hasRole("MANAGER")

                        // ✅ все остальные /api/** требуют авторизацию
                        .requestMatchers("/api/**").authenticated()

                        // ✅ всё остальное можно закрыть/открыть по желанию
                        .anyRequest().permitAll()
                )

                // ✅ formLogin можно оставить, но он тебе не обязателен
                .formLogin(Customizer.withDefaults())

                // ✅ logout (лучше явно)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    // ✅ чтобы работал authenticationManager в AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ password encoder (ТОЛЬКО ОДИН)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
