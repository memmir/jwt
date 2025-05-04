package com.security.jwt.config;

import com.security.jwt.jwtbusiness.AuthEntryPoint;
import com.security.jwt.jwtbusiness.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig { // Hangi url isteklerine izin verileceğine hangilerine yetkilendirmeye tabi tutulacağını belirleyeceğimiz config class ı.

    public static final String AUTHENTICATE = "/authenticate";
    public static final String REGISTER = "/register";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(request ->
                        request.requestMatchers(AUTHENTICATE, REGISTER)
                                .permitAll() //  AUTHENTICATE ve  REGISTER adreslerinden istek gelirse controller a sıkıntısız kabul et diyoruz.
                                .anyRequest()
                                .authenticated()) // üstteki 2 adres harici diğer adresleri de kontrol et diyoruz.
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and() // Herhangi bir hata veya yetkisizlik  olduğunda filter katmanından AuthEntryPoint classıma yönlendir dedik.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
