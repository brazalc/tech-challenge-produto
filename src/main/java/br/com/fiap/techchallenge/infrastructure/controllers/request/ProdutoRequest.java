package br.com.fiap.techchallenge.infrastructure.controllers.request;

import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.domain.enums.Tipo;
import br.com.fiap.techchallenge.infrastructure.controllers.request.enums.TipoRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProdutoRequest {

    private String nome;
    private BigDecimal preco;
    private String descricao;
    private List<IngredienteRequest> ingredientes;
    private TipoRequest tipo;

    public Produto toDomain() {
        return Produto.builder()
                .id(UUID.randomUUID())
                .nome(this.nome)
                .preco(this.preco)
                .descricao(this.descricao)
                .ingredientes(this.ingredientes.stream().map(IngredienteRequest::toDomain).toList())
                .tipo(Tipo.fromValue(this.tipo.getValue()))
                .build();
    }
}
