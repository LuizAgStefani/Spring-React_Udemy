/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.model.repository;


import com.luiz.minhasfinancas.model.entity.Usuario;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
public class UsuarioRepositoryTest {
    
    @Autowired
    UsuarioRepository repository;
    
    @Autowired
    TestEntityManager entityManager;
    
    @Test
    public void  deveVerificarAExistenciaDeUmEmail(){
        //cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);
        
        //ação/execução
        boolean result = repository.existsByEmail("usuario@email.com");
        
        //verificação
        Assertions.assertThat(result).isTrue();
        
     }
    
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuaruiCadastroComOEmail(){

        //ação/execução
        boolean result = repository.existsByEmail("usuario@email.com");
        
        //verificação
        Assertions.assertThat(result).isFalse();
    }
    
    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenário
        Usuario usuario = criarUsuario();
        
        //acao
        Usuario usuarioSalvo = repository.save(usuario);
        
        //verificação
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }
    
    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        //cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);
        
        //ação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        
        //verificação
        Assertions.assertThat(result.isPresent()).isTrue();
        
    }
    
    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioPorEmail(){

        //ação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");
        
        //verificação
        Assertions.assertThat(result.isPresent()).isFalse();
        
    }
    
    public static Usuario criarUsuario(){
        return Usuario
                .builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();
    }
}
