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
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author luiz
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmLancamento() {
        Lancamento lancamento = criarLancamento();

        lancamento = repository.save(lancamento);

        Assertions.assertNotNull(lancamento.getId());
    }

    @Test
    public void deveDeletarUmLancamento() {
        Lancamento lancamento =  criarLancamento();
        entityManager.persist(lancamento);
        
        lancamento = entityManager.find(Lancamento.class, lancamento.getId());
        
        repository.delete(lancamento);
        
        Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
        
        Assertions.assertNull(lancamentoInexistente);
    }

    private static Lancamento criarLancamento() {
        return Lancamento.builder()
                .ano(2019)
                .mes(1)
                .descricao("lancamento qualquer")
                .valor(BigDecimal.valueOf(10))
                .tipo(TipoLancamento.RECEITA)
                .status(StatusLancamento.PENDENTE)
                .dataCadastro(LocalDate.now())
                .build();
    }

}
