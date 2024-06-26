package br.com.fiap.techchallenge.infrastructure.controller;

import br.com.fiap.techchallenge.application.usecases.combo.CriaComboInteractor;
import br.com.fiap.techchallenge.application.usecases.combo.ListaComboInteractor;
import br.com.fiap.techchallenge.domain.Combo;
import br.com.fiap.techchallenge.domain.Ingrediente;
import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.domain.enums.Tipo;
import br.com.fiap.techchallenge.infrastructure.controllers.ComboController;
import br.com.fiap.techchallenge.infrastructure.controllers.mappers.ComboDtoMapper;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ComboRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.IngredienteRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ProdutoRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.enums.TipoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ComboController.class)
public class ComboControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriaComboInteractor criaComboInteractor;

    @MockBean
    private ListaComboInteractor listaComboInteractor;

    @MockBean
    private ComboDtoMapper comboDtoMapper;

    @Test
    public void deveCriarComboValido() throws Exception {
        UUID id = UUID.randomUUID();
        ProdutoRequest produtoRequest = ProdutoRequest.builder()
                .nome("Hamburguer")
                .descricao("Hamburguer de Carne Bovina com Queijo")
                .preco(BigDecimal.TEN)
                .tipo(TipoRequest.LANCHE)
                .ingredientes(List.of(IngredienteRequest.builder()
                        .descricao("Carne Bovina")
                        .build()))
                .build();

        ComboRequest comboRequestTest = ComboRequest.builder()
                .produtos(List.of(produtoRequest))
                .build();

        Ingrediente ingrediente = Ingrediente.criaIngrediente(id, "descricao");
        Produto lanche = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Produto")
                .preco(BigDecimal.TEN)
                .descricao("Descricao")
                .ingredientes(List.of(ingrediente))
                .tipo(Tipo.LANCHE)
                .build();

        Produto bebida = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Produto")
                .preco(BigDecimal.TEN)
                .descricao("Descricao")
                .ingredientes(List.of(ingrediente))
                .tipo(Tipo.BEBIDA)
                .build();

        Produto acompanhamento = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Produto")
                .preco(BigDecimal.TEN)
                .descricao("Descricao")
                .ingredientes(List.of(ingrediente))
                .tipo(Tipo.ACOMPANHAMENTO)
                .build();

        Combo combo = Combo.criaCombo(id, List.of(lanche, bebida, acompanhamento));

        when(criaComboInteractor.execute(any(ComboRequest.class))).thenReturn(combo);


        mockMvc.perform(post("/api/combos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comboRequestTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.precoTotal").exists())
                .andExpect(jsonPath("$.produtos").isArray());
    }

    @Test
    public void deveRetornarBadRequestQuandoListaDeProdutosForNula() throws Exception {
        ComboRequest comboRequestTest = ComboRequest.builder()
                .produtos(null)
                .build();

        when(criaComboInteractor.execute(any(ComboRequest.class)))
                .thenThrow(new IllegalArgumentException("Lista de produtos deve estar preenchida"));

        mockMvc.perform(post("/api/combos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comboRequestTest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestQuandoListaDeProdutosForVazia() throws Exception {
        ComboRequest comboRequestTest = ComboRequest.builder()
                .produtos(List.of())
                .build();

        when(criaComboInteractor.execute(any(ComboRequest.class)))
                .thenThrow(new IllegalArgumentException("Lista de produtos deve estar preenchida"));

        mockMvc.perform(post("/api/combos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comboRequestTest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestQuandoListaDeProdutosForInvalida() throws Exception {
        ComboRequest comboRequestTest = ComboRequest.builder()
                .produtos(List.of())
                .build();

        when(criaComboInteractor.execute(any(ComboRequest.class)))
                .thenThrow(new IllegalArgumentException("Lista de produtos deve estar preenchida"));

        mockMvc.perform(post("/api/combos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comboRequestTest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarUmaListaDeCombos() throws Exception {
        UUID id = UUID.randomUUID();

        Ingrediente ingrediente = Ingrediente.criaIngrediente(id, "descricao");

        Produto lanche = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Produto")
                .preco(BigDecimal.TEN)
                .descricao("Descricao")
                .ingredientes(List.of(ingrediente))
                .tipo(Tipo.LANCHE)
                .build();

        Produto bebida = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Produto")
                .preco(BigDecimal.TEN)
                .descricao("Descricao")
                .ingredientes(List.of(ingrediente))
                .tipo(Tipo.BEBIDA)
                .build();

        Produto acompanhamento = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Produto")
                .preco(BigDecimal.TEN)
                .descricao("Descricao")
                .ingredientes(List.of(ingrediente))
                .tipo(Tipo.ACOMPANHAMENTO)
                .build();

        Combo combo = Combo.criaCombo(UUID.randomUUID(), List.of(lanche, bebida, acompanhamento));
        when(listaComboInteractor.execute()).thenReturn(List.of(combo));
        mockMvc.perform(get("/api/combos")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}