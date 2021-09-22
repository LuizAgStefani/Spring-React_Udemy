/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.api;

import com.luiz.minhasfinancas.service.JwtService;
import com.luiz.minhasfinancas.service.impl.SecurityUserDetailsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author luiz
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private SecurityUserDetailsService userDetailsService;

    @Autowired // tirar em caso de problema
    public JwtTokenFilter(
            JwtService jwtService,
            SecurityUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //To change body of generated methods, choose Tools | Templates.
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            boolean isTokenValid = jwtService.isTokenValido(token);

            if (isTokenValid) {
                String login = jwtService.obterLoginUsuario(token);
                UserDetails usuarioAutenticado = userDetailsService.loadUserByUsername(login);

                UsernamePasswordAuthenticationToken user
                        = new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, usuarioAutenticado.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(user);

            }
        }
        filterChain.doFilter(request, response);
    }

}
