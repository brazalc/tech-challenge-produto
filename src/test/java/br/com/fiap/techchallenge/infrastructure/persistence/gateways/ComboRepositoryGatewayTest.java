package br.com.fiap.techchallenge.infrastructure.persistence.gateways;

import br.com.fiap.techchallenge.domain.Combo;
import br.com.fiap.techchallenge.domain.Ingrediente;
import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.domain.enums.Tipo;
import br.com.fiap.techchallenge.infrastructure.persistence.entity.ComboEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.repository.ComboRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ComboRepositoryGatewayTest {


    private ComboRepository comboRepository;

    private ComboRepositoryGateway comboRepositoryGateway;

    @BeforeEach
    void setUp() {
        comboRepository = mock(ComboRepository.class);
        comboRepositoryGateway = new ComboRepositoryGateway(comboRepository);
    }

    @Test
    void deveSalvarComboValido() {
        Produto lanche = Produto.builder()
                .id(UUID.randomUUID())
                .nome("X-Bacon")
                .preco(BigDecimal.TEN)
                .descricao("Lanche")
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Hamburguer")))
                .tipo(Tipo.LANCHE)
                .build();

        Produto bebida = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Coca-Cola")
                .preco(BigDecimal.TEN)
                .descricao("Bebida")
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Cola")))
                .tipo(Tipo.BEBIDA)
                .build();

        Produto sobremesa = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Sorvete")
                .preco(BigDecimal.TEN)
                .descricao("Sobremesa")
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Creme de sorvete")))
                .tipo(Tipo.ACOMPANHAMENTO)
                .build();

        Combo combo = Combo.criaCombo(UUID.randomUUID(), List.of(lanche, bebida, sobremesa));
        ComboEntity comboEntity = ComboEntity.criaComboEntity(combo);
        when(comboRepository.save(any())).thenReturn(comboEntity);


        Combo result = comboRepositoryGateway.salva(combo);


        assertEquals(combo, result);
        verify(comboRepository, times(1)).save(any());
    }

    @Test
    void deveListarTodosOsCombosSalvos() {

        Produto lanche = Produto.builder()
                .id(UUID.randomUUID())
                .nome("X-Bacon")
                .preco(BigDecimal.TEN)
                .descricao("Lanche")
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Hamburguer")))
                .tipo(Tipo.LANCHE)
                .build();

        Produto bebida = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Coca-Cola")
                .preco(BigDecimal.TEN)
                .descricao("Bebida")
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Cola")))
                .tipo(Tipo.BEBIDA)
                .build();

        Produto sobremesa = Produto.builder()
                .id(UUID.randomUUID())
                .nome("Sorvete")
                .preco(BigDecimal.TEN)
                .descricao("Sobremesa")
                .ingredientes(List.of(Ingrediente.criaIngrediente(UUID.randomUUID(), "Creme de sorvete")))
                .tipo(Tipo.ACOMPANHAMENTO)
                .build();

        Combo combo = Combo.criaCombo(UUID.randomUUID(), List.of(lanche, bebida, sobremesa));
        ComboEntity comboEntity1 = ComboEntity.criaComboEntity(combo);
        List<ComboEntity> comboEntities = List.of(comboEntity1);
        when(comboRepository.findAll()).thenReturn(comboEntities);


        List<Combo> result = comboRepositoryGateway.listaCombos();


        List<Combo> expectedCombos = comboEntities.stream()
                .map(ComboEntity::toDomain)
                .collect(Collectors.toList());
        assertEquals(expectedCombos, result);
        verify(comboRepository, times(1)).findAll();
    }
}
