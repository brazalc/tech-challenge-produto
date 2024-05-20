package br.com.fiap.techchallenge.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.fiap.techchallenge.domain.Ingrediente;
import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.domain.enums.Tipo;
import br.com.fiap.techchallenge.infrastructure.persistence.entity.enums.TipoEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Produtos")
public class ProdutoEntity {

    private UUID id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private List<IngredienteEntity> ingredientes;

    private TipoEntity tipo;

    public Produto toDomain() {
        List<Ingrediente> ingredientes = this.ingredientes.stream()
                .map(IngredienteEntity::toDomain)
                .toList();

        return Produto.builder()
                .id(this.id)
                .nome(this.nome)
                .descricao(this.descricao)
                .preco(this.preco)
                .ingredientes(ingredientes)
                .tipo(Tipo.fromValue(this.tipo.getValue()))
                .build();
    }

    public static List<Produto> toDomain(List<ProdutoEntity> produtoEntities) {
        return produtoEntities.stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }

    public static ProdutoEntity toEntity(Produto produto) {
        List<IngredienteEntity> ingredientesEntity = produto.getIngredientes().stream()
                .map(IngredienteEntity::criaEntity).toList();

        return new ProdutoEntity(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(),
                ingredientesEntity,
                TipoEntity.valueOf(produto.getTipo().name()));
    }

    public static List<ProdutoEntity> toEntity(List<Produto> produtos) {
        return produtos.stream()
                .map(ProdutoEntity::toEntity)
                .collect(Collectors.toList());
    }
}
