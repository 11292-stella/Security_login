package com.example.Security_login.Security;

import com.example.Security_login.Exception.UnAuthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        1. verificare se la richiesta ha il token
        2. se non ha il token, eccezione
        3. se ha il token, viene verificato che il token sia valido. Se non è valido, eccezione
        4. se il token è valido, allora si farà accedere la richiesta ai filtri successivi
         */

        String authorization = request.getHeader("Authorization");

        if(authorization==null || !authorization.startsWith("Bearer ")){
            throw new UnAuthorizedException("Token non presente, non sei autorizzato ad usare il servizio richiesto");
        }
        else{
            //estraggo il token dalla stringa authorization che contiene anche la parola Bearer  prima del token. Per questo prendo solo
            //la parte della stringa che comincia dal carattere 7
            String token = authorization.substring(7);

            //verifico che il token sia valido
            jwtTool.validateToken(token);

            filterChain.doFilter(request, response);
        }

    }

    //questo metodo evita che gli endpoint di registrazione e login possano richiedere il token
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
