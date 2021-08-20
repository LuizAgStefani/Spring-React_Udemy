/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.service;

import com.luiz.minhasfinancas.exception.RegraNegocioException;
import com.luiz.minhasfinancas.model.entity.Usuario;
import com.luiz.minhasfinancas.model.repository.UsuarioRepository;
import com.luiz.minhasfinancas.service.impl.UsuarioServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    UsuarioService service;
    
    @MockBean
    UsuarioRepository repository;
    
    @BeforeEach
    public void setUp() {
        service = new UsuarioServiceImpl(repository);
    }
    
    @Test
    public void deveAutenticarUmUsuarioComSucesso(){
        //cenário
        String email = "email@email.com.br";
        String senha = "senha";
        
        Usuario usuario = Usuario
                .builder()
                .email(email)
                .senha(senha)
                .id(1l)
                .build();
        
        Mockito.when( repository.findByEmail(email) ).thenReturn(Optional.of(usuario));
        
        //ação
        Usuario result = service.autenticar(email, senha);
        
        //verificação
        Assertions.assertNotNull(result);
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
