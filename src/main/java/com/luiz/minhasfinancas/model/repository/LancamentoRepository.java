/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.model.repository;

import com.luiz.minhasfinancas.model.entity.Lancamento;
import com.luiz.minhasfinancas.model.enums.StatusLancamento;
import com.luiz.minhasfinancas.model.enums.TipoLancamento;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author luiz
 */
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>{
    
    @Query(value = "select sum(l.valor) from Lancamento l join l.usuario u "
            + "where u.id = :idUsuario and l.tipo =:tipo and l.status = :status group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatus(
            @Param("idUsuario") Long idUsuario,
            @Param("tipo") TipoLancamento tipo,
            @Param("status") StatusLancamento status);
    
}
