/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service;

import com.luiz.minhasfinancas.exception.RegraNegocioException;
import com.luiz.minhasfinancas.model.entity.Lancamento;
import com.luiz.minhasfinancas.model.entity.Usuario;
import com.luiz.minhasfinancas.model.enums.StatusLancamento;
import com.luiz.minhasfinancas.model.repository.LancamentoRepository;
import static com.luiz.minhasfinancas.model.repository.LancamentoRepositoryTest.*;
import com.luiz.minhasfinancas.service.impl.LancamentoServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author luiz
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImpl service;

    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento() {
        //cenário
        Lancamento lancamentoASalvar = criarLancamento();
        Mockito.doNothing().when(service).validar(lancamentoASalvar);

        Lancamento lancamentoSalvo = criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        //execução
        Lancamento lancamento = service.salvar(lancamentoASalvar);

        //verificação
        assertEquals(lancamentoSalvo.getId(), lancamento.getId());
        assertEquals(StatusLancamento.PENDENTE, lancamento.getStatus());
    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao() {
        //cenário
        Lancamento lancamentoASalvar = criarLancamento();
        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

        assertThrows(RegraNegocioException.class, () -> {
            service.salvar(lancamentoASalvar);
        });

        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveAtualizarUmLancamento() {
        //cenário
        Lancamento lancamentoSalvo = criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

        Mockito.doNothing().when(service).validar(lancamentoSalvo);

        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        //execução
        service.atualizar(lancamentoSalvo);

        //verificação
        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);
    }

    @Test
    public void deveLancarErroAoTentarAtualizarLancamentoQueAindaNaoFoiSalvo() {
        //cenário
        Lancamento lancamentoASalvar = criarLancamento();

        NullPointerException assertThrows = assertThrows(NullPointerException.class, () -> {
            service.atualizar(lancamentoASalvar);
        });

        assertEquals(null, assertThrows.getMessage());//não é necessário
        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveDeletarUmLancamento() {
        //cenário
        Lancamento lancamento = criarLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamento.PENDENTE);

        Mockito.doNothing().when(service).validar(lancamento);

        Mockito.when(repository.save(lancamento)).thenReturn(lancamento);

        //execução
        service.deletar(lancamento);

        //verificação
        Mockito.verify(repository, Mockito.times(1)).delete(lancamento);
    }

    @Test
    public void deveLancarErroAoDeletarLancamentoQueNaoExiste() {
        //cenário
        Lancamento lancamento = criarLancamento();

        //execução
        NullPointerException assertThrows = assertThrows(NullPointerException.class, () -> {
            service.deletar(lancamento);
        });

        //verificação
        assertEquals(null, assertThrows.getMessage()); //não é necessário
        Mockito.verify(repository, Mockito.never()).delete(lancamento);
    }

    @Test
    public void deveFiltrarLancamentos() {
        //cenário
        Lancamento lancamento = criarLancamento();
        lancamento.setId(1l);

        List<Lancamento> lista = Arrays.asList(lancamento);
        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        //execução
        List<Lancamento> resultado = service.buscar(lancamento);

        //verificacoes
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.size() == 1);
        assertTrue(resultado.contains(lancamento));
    }

    @Test
    public void deveAtualizarOStatusDeUmLancamento() {
        //cenário
        Lancamento lancamento = criarLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        //execução
        service.atualiarStatus(lancamento, novoStatus);

        //verificação
        assertEquals(novoStatus, lancamento.getStatus());
        Mockito.verify(service).atualizar(lancamento);

    }

    @Test
    public void deveObterUmLancamentoPorID() {
        //cenário
        Long id = 1l;

        Lancamento lancamento = criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

        //execução
        Optional<Lancamento> resultado = service.obterPorId(id);

        //verificação
        assertTrue(resultado.isPresent());
    }

    @Test
    public void deveRetornarVazioQuandoOLancamentoNaoExiste() {
        //cenário
        Long id = 1l;

        Lancamento lancamento = criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //execução
        Optional<Lancamento> resultado = service.obterPorId(id);

        //verificação
        assertFalse(resultado.isPresent());
    }

    @Test
    public void deveLancarErroQuandoADescricaoForNula() {
        //cenário
        Lancamento lancamento = new Lancamento();

        //execução
        
        // descrição
        RegraNegocioException erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe uma Descrição válida.", erro.getMessage());

        lancamento.setDescricao("");
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe uma Descrição válida.", erro.getMessage());

        lancamento.setDescricao("Salario");

        //mês
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Mês válido.", erro.getMessage());
        
        lancamento.setMes(0);
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Mês válido.", erro.getMessage());
        
        lancamento.setMes(13);
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Mês válido.", erro.getMessage());
        
        lancamento.setMes(1);
        
        //ano
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Ano válido.", erro.getMessage());
        
        lancamento.setAno(20158);
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Ano válido.", erro.getMessage());
        
        lancamento.setAno(2021);
        
        //usuário
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Usuário.", erro.getMessage());

        lancamento.setUsuario(new Usuario());
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Usuário.", erro.getMessage());
        
        lancamento.getUsuario().setId(1l);
        
        //Valor
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Valor válido.", erro.getMessage());
        
        lancamento.setValor(BigDecimal.ZERO);
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Valor válido.", erro.getMessage());
        
        lancamento.setValor(BigDecimal.valueOf(1));
        
        erro = assertThrows(RegraNegocioException.class, () -> {
            service.validar(lancamento);
        });
        assertEquals("Informe um Tipo de Lançamento válido.", erro.getMessage());
    }

}
