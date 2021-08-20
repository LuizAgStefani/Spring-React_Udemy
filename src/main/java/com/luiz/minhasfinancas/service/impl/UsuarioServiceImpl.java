/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service.impl;

import com.luiz.minhasfinancas.model.entity.Usuario;
import com.luiz.minhasfinancas.model.repository.UsuarioRepository;
import com.luiz.minhasfinancas.service.UsuarioService;

/**
 *
 * @author luiz
 */
public class UsuarioServiceImpl implements UsuarioService{
    
    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {

    }
    
}
