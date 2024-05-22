package br.com.fiap.techchallenge.application.usecases.combo;

import br.com.fiap.techchallenge.application.gateways.ComboGateway;
import br.com.fiap.techchallenge.application.gateways.ProdutoGateway;
import br.com.fiap.techchallenge.domain.Combo;
import br.com.fiap.techchallenge.domain.Ingrediente;
import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.domain.enums.Tipo;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ComboRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.IngredienteRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ProdutoRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.enums.TipoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CriaComboInteractorTest {

    @Mock
    private ComboGateway comboGateway;

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private CriaComboInteractor criaComboInteractor;

    @BeforeEach
    void setUp() {
        comboGateway = mock(ComboGateway.class);
        produtoGateway = mock(ProdutoGateway.class);

        criaComboInteractor = new CriaComboInteractor(comboGateway, produtoGateway);
    }

    @Test
    void deveCriarCombo() {

        ProdutoRequest lancheRequest = ProdutoRequest.builder()
                .nome("Hamburguer")
                .descricao("Hamburguer de Carne Bovina com Queijo")
                .preco(BigDecimal.TEN)
                .tipo(TipoRequest.LANCHE)
                .ingredientes(List.of(IngredienteRequest.builder()
                        .descricao("Carne Bovina")
                        .build()))
                .build();

        ProdutoRequest bebidaRequest = ProdutoRequest.builder()
                .nome("Suco de Laranja")
                .descricao("Suco de Laranja Natural")
                .preco(BigDecimal.valueOf(5))
                .tipo(TipoRequest.BEBIDA)
                .ingredientes(List.of(IngredienteRequest.builder()
                        .descricao("Laranja")
                        .build()))
                .build();
        ProdutoRequest acompanhamentoRequest = ProdutoRequest.builder()
                .nome("Batata Frita")
                .descricao("Porção de Batata Frita")
                .preco(BigDecimal.valueOf(7))
                .tipo(TipoRequest.ACOMPANHAMENTO)
                .ingredientes(List.of(IngredienteRequest.builder()
                        .descricao("Batata")
                        .build()))
                .build();


        ComboRequest comboRequest = ComboRequest.builder()
                .produtos(List.of(lancheRequest, bebidaRequest, acompanhamentoRequest))
                .build();

        Produto lanche = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Coca-Cola")
                .descricao("Refrigerante de cola")
                .preco(BigDecimal.TEN)
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Cola")))
                .tipo(Tipo.LANCHE)
                .build();

        Produto acompanhamento = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Batata Frita")
                .descricao("Batata frita com cheddar e bacon")
                .preco(BigDecimal.TEN)
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Batata")))
                .tipo(Tipo.ACOMPANHAMENTO)
                .build();

        Produto bebida = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Coca-Cola")
                .descricao("Refrigerante de cola")
                .preco(BigDecimal.TEN)
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Cola")))
                .tipo(Tipo.BEBIDA)
                .build();


        List<Produto> produtos = Arrays.asList(lanche, acompanhamento, bebida);

        when(produtoGateway.buscaPorUuids(anyList())).thenReturn(produtos);

        when(comboGateway.salva(any(Combo.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Combo result = criaComboInteractor.execute(comboRequest);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getProdutos());
        assertNotNull(result.getProdutos().get(0).getId());

        verify(comboGateway, times(1)).salva(any(Combo.class));
    }
}