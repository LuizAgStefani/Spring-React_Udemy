/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luiz.minhasfinancas.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luiz.minhasfinancas.api.dto.UsuarioDTO;
import com.luiz.minhasfinancas.exception.ErroAutenticacao;
import com.luiz.minhasfinancas.exception.RegraNegocioException;
import com.luiz.minhasfinancas.model.entity.Usuario;
import com.luiz.minhasfinancas.service.LancamentoService;
import com.luiz.minhasfinancas.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author luiz
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioResource.class)
@AutoConfigureMockMvc
public class UsuarioResourceTest {

    static final String API = "/api/usuarios";
    static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService service;
    
    @MockBean
    LancamentoService lancamentoService;

//    @Test
//    public void deveAutenticarUmUsuario() throws Exception {
//        String email = "usuario@email.com";
//        String senha = "123";
//
//        //cen??rio
//        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
//        Usuario usuario = Usuario.builder().id(1l).email(email).senha(senha).build();
//
//        Mockito.when(service.autenticar(email, senha)).thenReturn(usuario);
//
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        //execu????o e verifica????o
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post(API.concat("/autenticar"))
//                .accept(JSON)
//                .contentType(JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
//                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
//
//    }
    
//    @Test
//    public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
//        String email = "usuario@email.com";
//        String senha = "123";
//
//        //cen??rio
//        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
//
//        Mockito.when(service.autenticar(email, senha)).thenThrow(ErroAutenticacao.class);
//
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        //execu????o e verifica????o
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post(API.concat("/autenticar"))
//                .accept(JSON)
//                .contentType(JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//    }
//    
//    @Test
//    public void deveCriarUmNovoUsuario() throws Exception {
//        
//        String email = "usuario@email.com";
//        String senha = "123";
//
//        //cen??rio
//        UsuarioDTO dto = UsuarioDTO.builder().email("usuario@email.com").senha("123").build();
//        Usuario usuario = Usuario.builder().id(1l).email(email).senha(senha).build();
//
//        Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
//
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        //execu????o e verifica????o
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post( API )
//                .accept(JSON)
//                .contentType(JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
//                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
//
//    }
//    
//    @Test
//    public void deveRetornarBadRequestAoCadastrarUmNovoUsuario() throws Exception {
//        String email = "usuario@email.com";
//        String senha = "123";
//
//        //cen??rio
//        UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
//
//        Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);
//
//        String json = new ObjectMapper().writeValueAsString(dto);
//
//        //execu????o e verifica????o
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post(API)
//                .accept(JSON)
//                .contentType(JSON)
//                .content(json);
//
//        mvc
//                .perform(request)
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//    }

}
