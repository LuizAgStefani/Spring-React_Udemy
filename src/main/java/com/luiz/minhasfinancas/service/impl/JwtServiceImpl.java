/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service.impl;

import com.luiz.minhasfinancas.model.entity.Usuario;
import com.luiz.minhasfinancas.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author luiz
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiracao}")
    private String expiracao;
    @Value("${jwt.chave-assinatura}")
    private String chaveAssinatura;

    @Override
    public String gerarToken(Usuario usuario) {
        long expLong = Long.valueOf(expiracao);

        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expLong);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        String horaExpiracao = dataHoraExpiracao.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        String token = Jwts
                .builder()
                .setExpiration(data)
                .setSubject(usuario.getEmail())
                .claim("userid", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("horaExpiracao", horaExpiracao)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
        return token;
    }

    @Override
    public Claims obterClaims(String token) throws ExpiredJwtException {

        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isTokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExp = claims.getExpiration();

            LocalDateTime dataExpiracao = dataExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            boolean dataHoraAtualisBeforeDataExpiracao = LocalDateTime.now().isBefore(dataExpiracao);

            return dataHoraAtualisBeforeDataExpiracao;

        } catch (ExpiredJwtException e) {

            return false;

        }

    }

    @Override
    public String obterLoginUsuario(String token) {
        Claims claims = obterClaims(token);
        return claims.getSubject();
    }

}
