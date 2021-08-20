/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.exception;

/**
 *
 * @author luiz
 */
public class ErroAutenticacao extends RuntimeException {
    public ErroAutenticacao(String mensagem){
        super(mensagem);
    }
}
