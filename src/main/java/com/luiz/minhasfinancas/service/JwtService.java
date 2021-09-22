/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service;

import com.luiz.minhasfinancas.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/**
 *
 * @author luiz
 */
public interface JwtService {
    
    String gerarToken(Usuario usuario);
    
    Claims obterClaims(String token) throws ExpiredJwtException;
    
    boolean isTokenValido(String token);
    
    String obterLoginUsuario( String token );
}
