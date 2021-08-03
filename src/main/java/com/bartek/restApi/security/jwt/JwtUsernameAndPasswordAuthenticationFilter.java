package com.bartek.restApi.security.jwt;

import com.bartek.restApi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), User.class);
                                                                                      // Principle username              i    credentials password
              Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);// sprawdza czy podane dane są poprawne czyli username i password
            return authenticate;
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        //  CZYLI ZROBILISMY WYSLKE CREDENTIALS DO SERVERA I VALIDACJE TYCH DANYCH
    }

       //DO METODY DOJDZIEMY JAK AUTHENTICATION PRZEJDZIE
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
       //tworzymy JWT
//        String key = "securesecuresecuresecuresecuresecuresecuresecure";
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorites", authResult.getAuthorities())
                .setIssuedAt(new Date())
//                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
//                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .signWith(secretKey)
                .compact();

//        response.addHeader("Authorization", "Bearer "+token); //wysyłamy token do clienta
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix()+" "+token);//poprawiamy z uzyciem jwtConfig
        //                zwara nagłówek Autoryzacja                         a tutaj utworzony w properites prefix
    }
}
