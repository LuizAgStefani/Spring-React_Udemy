/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service.impl;

import com.luiz.minhasfinancas.exception.RegraNegocioException;
import com.luiz.minhasfinancas.model.entity.Lancamento;
import com.luiz.minhasfinancas.model.enums.StatusLancamento;
import com.luiz.minhasfinancas.model.enums.TipoLancamento;
import com.luiz.minhasfinancas.model.repository.LancamentoRepository;
import com.luiz.minhasfinancas.service.LancamentoService;

import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author luiz
 */
@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {

        Example<Lancamento> example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(StringMatcher.CONTAINING));

        return repository.findAll(example);
    }

    @Override
    public void atualiarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {

        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Informe uma Descri????o v??lida.");
        }

        if (Objects.isNull(lancamento.getMes()) || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new RegraNegocioException("Informe um M??s v??lido.");
        }

        if (Objects.isNull(lancamento.getAno()) || Integer.toString(lancamento.getAno()).length() != 4) {
            throw new RegraNegocioException("Informe um Ano v??lido.");
        }

        if (Objects.isNull(lancamento.getUsuario()) || Objects.isNull(lancamento.getUsuario().getId())) {
            throw new RegraNegocioException("Informe um Usu??rio.");
        }

        if (Objects.isNull(lancamento.getValor()) || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocioException("Informe um Valor v??lido.");
        }

        if (Objects.isNull(lancamento.getTipo())) {
            throw new RegraNegocioException("Informe um Tipo de Lan??amento v??lido.");
        }

    }

    @Override
    public Optional<Lancamento> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id) {
        BigDecimal receitas = repository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO);
        BigDecimal despesas = repository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO);

        if (receitas == null) {
            receitas = BigDecimal.ZERO;
        }

        if (despesas == null) {
            despesas = BigDecimal.ZERO;
        }
        
        return receitas.subtract(despesas);
    }

}
