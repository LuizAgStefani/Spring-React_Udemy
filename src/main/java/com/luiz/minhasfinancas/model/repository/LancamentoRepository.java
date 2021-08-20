/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.model.repository;

import com.luiz.minhasfinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author luiz
 */
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>{
    
}
