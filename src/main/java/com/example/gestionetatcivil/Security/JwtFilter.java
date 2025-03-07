package com.example.gestionetatcivil.Security;

import com.example.gestionetatcivil.Entities.Jwt;
import com.example.gestionetatcivil.Service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Service
public class JwtFilter  extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountService subscriberService;
    private final HandlerExceptionResolver handlerExceptionResolver;// gestion des exceptions


    public JwtFilter(JwtService jwtService, AccountService subscriberService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.subscriberService = subscriberService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<Jwt> tokendanslaBD = null;
        String email = null;
        String token = null;
        Boolean isTokenExpired = true;


    try {


        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            token = authorization.substring(7);
            tokendanslaBD= Optional.ofNullable((Jwt) this.jwtService.tokenByValue(token));
            isTokenExpired =  jwtService.isTokenExpired(token);
            email = jwtService.extractUsername(token);


        }
        if( !isTokenExpired
                && email!=null
                &&  tokendanslaBD.get().getSubscriber().getEmail().equals(email)
                && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = subscriberService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);

    }
    catch (Exception exception) { handlerExceptionResolver.resolveException(request, response, null, exception);                          }
    }
}
