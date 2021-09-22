/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author luiz
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {
    
    private String nome;
    private String token;
    
}
