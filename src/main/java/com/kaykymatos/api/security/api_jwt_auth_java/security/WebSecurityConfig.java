package com.kaykymatos.api.security.api_jwt_auth_java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("123456"))
                .roles("USERS")
                .build();

        UserDetails admin = User.withUsername("manager")
                .password(passwordEncoder.encode("manager"))
                .roles("MANAGERS")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(HttpMethod.GET,"/").permitAll()
            .requestMatchers(HttpMethod.POST,"/login").permitAll() // <- ESSA LINHA É ESSENCIAL!
            .requestMatchers(HttpMethod.GET,"/managers").hasRole("MANAGERS")
            .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("USERS", "MANAGERS")
            .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults());

    return http.build();
}


}
