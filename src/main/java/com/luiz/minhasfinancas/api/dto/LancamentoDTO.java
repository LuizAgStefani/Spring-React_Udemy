/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.api.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author luiz
 */
@Data
@Builder
public class LancamentoDTO {

    private Long id;
    
    private String descricao;
    
    private Integer mes;
    
    private Integer ano;
    
    private BigDecimal valor;
    
    private Long usuario;
    
    private String tipo;
    
    private String status;
    
}
