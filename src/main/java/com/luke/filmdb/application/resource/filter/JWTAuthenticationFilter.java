package com.luke.filmdb.application.resource.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.luke.filmdb.config.SecurityConstants.HEADER_STRING;
import static com.luke.filmdb.config.SecurityConstants.TOKEN_PREFIX;
import static com.luke.filmdb.security.jwt.TokenProvider.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.luke.filmdb.security.jwt.TokenProvider.SIGNING_KEY;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            com.luke.filmdb.application.model.user.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), com.luke.filmdb.application.model.user.User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

//        String token = JWT.create()
//                .withSubject(((User) auth.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(HS256);
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .signWith(HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

}