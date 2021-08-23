/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.model.entity;

import com.luiz.minhasfinancas.model.enums.TipoLancamento;
import com.luiz.minhasfinancas.model.enums.StatusLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 *
 * @author luiz
 */
@Data
@Builder
@Entity
@Table(name = "lancamento", schema = "financas")
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="descricao")
    private String descricao;
    
    @Column(name="mes")
    private int mes;
    
    @Column(name="ano")
    private int ano;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @Column(name="valor")
    private BigDecimal valor;
    
    @Column(name="data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;
    
    @Column(name="tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;
    
    @Column(name="status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;
    
}
