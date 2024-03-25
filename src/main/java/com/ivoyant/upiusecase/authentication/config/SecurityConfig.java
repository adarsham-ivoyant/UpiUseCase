package com.ivoyant.upiusecase.authentication.config;

import com.ivoyant.upiusecase.authentication.service.CustomUserDetailService;
import com.ivoyant.upiusecase.authentication.service.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomSuccessHandler customSuccessHandler;

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler, CustomUserDetailService customUserDetailService) {
        this.customSuccessHandler = customSuccessHandler;
        this.customUserDetailService = customUserDetailService;
    }

    // Configuring BCryptPassword Encoder as Password Encoder
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Implementation Of Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disabling CSRF Token
        // Permitting All User To Access LandingPage, LoginPage, RegistrationPage, Along CSS and JS files
        // Overriding Default Login Page Of Spring Security
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/getlist").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/register").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").successHandler(customSuccessHandler)
                        .permitAll())
                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout").permitAll());
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
