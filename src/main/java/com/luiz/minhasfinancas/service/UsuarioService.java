/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service;

import com.luiz.minhasfinancas.model.entity.Usuario;

/**
 *
 * @author luiz
 */
public interface UsuarioService {
    Usuario autenticar(String email, String senha);
    
    Usuario salvarUsuario(Usuario usuario);
    
    void validarEmail(String email);
}
