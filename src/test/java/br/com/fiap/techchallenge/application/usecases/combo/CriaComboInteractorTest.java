package br.com.fiap.techchallenge.application.usecases.combo;

import br.com.fiap.techchallenge.application.gateways.ComboGateway;
import br.com.fiap.techchallenge.domain.Combo;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ComboRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.IngredienteRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ProdutoRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.enums.TipoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CriaComboInteractorTest {

    @Mock
    private ComboGateway comboGateway;

    @InjectMocks
    private CriaComboInteractor criaComboInteractor;

    @BeforeEach
    void setUp() {
        comboGateway = mock(ComboGateway.class);

        criaComboInteractor = new CriaComboInteractor(comboGateway);
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