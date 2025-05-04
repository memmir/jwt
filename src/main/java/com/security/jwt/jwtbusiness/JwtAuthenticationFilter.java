package com.security.jwt.jwtbusiness;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //Gelecek token yapısı aşağıdaki gibi olacak. "Bearer" + " " + "{token}"
        //Bearer oasgladglşadgldakgadpgdaligadladlgadkg
        String header;
        String token;
        String username;

        header = request.getHeader("Authorization");

        if(header == null) {
            filterChain.doFilter(request, response); //header null değerse hiç aşağıya inmeden süreci bitiriyoruz
            return;
        }

        token = header.substring(7); //ilk 7 karakteri yoksaydık. Bearer gitti sadece tokeni aldık.

        try{

            username = jwtService.getUsernameByToken(token);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // loadByUsername metodu vevritabanını kontrol eder, tokende gelen ismin aynısından veritabanında var mı diye.
                if(userDetails != null && jwtService.isTokenExpired(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // bu satır ile gelen tokeni ve tokenin içindeki verileri security contexe verdik demek.
                    // Security contexe vermek demek ise içindeki veriler filterdan başarı ile geçti ve controller a ulaştı anlamına gelmekte.
                }
            }

        }catch (ExpiredJwtException e) {
            System.out.println("JWT token expired: " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
