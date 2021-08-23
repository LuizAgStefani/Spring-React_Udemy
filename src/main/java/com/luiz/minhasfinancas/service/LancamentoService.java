/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service;

import com.luiz.minhasfinancas.model.entity.Lancamento;
import com.luiz.minhasfinancas.model.enums.StatusLancamento;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author luiz
 */
public interface LancamentoService {
    
    Lancamento salvar(Lancamento lancamento);
    
    Lancamento atualizar(Lancamento lancamento);
    
    void deletar(Lancamento lancamento);
    
    List<Lancamento> buscar ( Lancamento lancamentoFiltro );
    
    void atualiarStatus(Lancamento lancamento, StatusLancamento status);
    
    void validar(Lancamento lancamento);
    
    Optional<Lancamento> obterPorId(Long id);
}
