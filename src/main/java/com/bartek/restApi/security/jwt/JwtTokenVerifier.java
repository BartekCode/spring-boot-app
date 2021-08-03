package com.bartek.restApi.security.jwt;

import com.bartek.restApi.security.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private Role role;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        String authorizationHeader = request.getHeader("Authorization");
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
//        if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
        if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        try {
//            String secretKey = "securesecuresecuresecuresecuresecuresecuresecure";

            JwtParser jwtParser = Jwts.parserBuilder()
//                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .setSigningKey(secretKey)
                    .build();
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            var authorities = (List<Map<String, String>>) body.get("authorities");

            //to nie działa zrobiłem swoje SimpleGrantedAtuhority wyciągnąłem z klasy Role
//            List<String> authorities1 = claimsJws.getBody().get("authorities", List.class);
//
//            List <SimpleGrantedAuthority> simpleauth = authorities.stream()
//                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
//                    .collect(Collectors.toList());


            List<Role> collect = Arrays.stream(Role.values()).collect(Collectors.toList());
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = collect.stream()
                    .map(m -> new SimpleGrantedAuthority(m.getAuthority()))
                    .collect(Collectors.toList());


                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch(JwtException e){
                throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
            }
            filterChain.doFilter(request, response);
        } //token musi przejsc naszą validacje
    }
