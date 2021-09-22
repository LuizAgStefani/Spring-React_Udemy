/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service;

import com.luiz.minhasfinancas.exception.ErroAutenticacao;
import com.luiz.minhasfinancas.exception.RegraNegocioException;
import com.luiz.minhasfinancas.model.entity.Usuario;
import com.luiz.minhasfinancas.model.repository.UsuarioRepository;
import com.luiz.minhasfinancas.service.impl.UsuarioServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author luiz
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

//    @Test
//    public void deveSalvarUmUsuario() {
//        //cenário
//        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
//        Usuario usuario = Usuario.builder()
//                .id(1l)
//                .nome("nome")
//                .email("email@email.com")
//                .senha("senha")
//                .build();
//        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
//        //ação
//        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
//        //verificação
//        Assertions.assertNotNull(usuarioSalvo);
//        Assertions.assertEquals(usuarioSalvo.getId(), 1l);
//        Assertions.assertEquals(usuarioSalvo.getNome(), "nome");
//        Assertions.assertEquals(usuarioSalvo.getEmail(), "email@email.com");
//        Assertions.assertEquals(usuarioSalvo.getSenha(), "senha");
//
//    }

    @Test
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
        //cenário
        String email = "email@email.com";
        Usuario user = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        //ação
        RegraNegocioException exception = Assertions.assertThrows(RegraNegocioException.class, () -> {
            service.salvarUsuario(user);
        });

        //verificação
        Mockito.verify(repository, Mockito.never()).save(user);
    }

//    @Test
//    public void deveAutenticarUmUsuarioComSucesso() {
//        //cenário
//        String email = "email@email.com.br";
//        String senha = "senha";
//
//        Usuario usuario = Usuario
//                .builder()
//                .email(email)
//                .senha(senha)
//                .id(1l)
//                .build();
//
//        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
//
//        //ação
//        Usuario result = service.autenticar(email, senha);
//
//        //verificação
//        Assertions.assertNotNull(result);
//    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastroComOEmailInformado() {

        //cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //ação
        ErroAutenticacao erro = Assertions.assertThrows(ErroAutenticacao.class, () -> {
            service.autenticar("email@email.com", "senha");
        });

        String esperada = "Usuário não encontrado para o email informado.";
        String erroAtual = erro.getMessage();

        assertTrue(erroAtual.contains(esperada));

    }

    @Test
    public void deveLancarErroQuandoSenhaNaoBater() {
        //cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //ação
        ErroAutenticacao erro = Assertions.assertThrows(ErroAutenticacao.class, () -> {
            service.autenticar("email@email.com", "erro");
        });

        String mensagemEsperada = "Senha inválida.";
        String mensagemAtual = erro.getMessage();

        assertTrue(mensagemEsperada.contains(mensagemAtual));
    }

    @Test
    public void deveValidarEmail() {
        //cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        //acao
        service.validarEmail("email@email.com");
    }

    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao
        RegraNegocioException exception = Assertions.assertThrows(RegraNegocioException.class, () -> {
            service.validarEmail("email@email.com");
        });

        String mensagemEsperada = "Já existe um usuário cadastrado com este email.";
        String mensagemAtual = exception.getMessage();

        assertTrue(mensagemAtual.contains(mensagemEsperada));
    }
}
