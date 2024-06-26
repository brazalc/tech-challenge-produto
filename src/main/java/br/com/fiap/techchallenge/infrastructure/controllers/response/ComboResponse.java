package br.com.fiap.techchallenge.infrastructure.controllers.response;

import br.com.fiap.techchallenge.domain.Combo;
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
public class ComboResponse {
    private UUID id;

    private BigDecimal precoTotal;

    private List<ProdutoResponse> produtos;

    public static ComboResponse fromDomain(Combo combo) {
        return ComboResponse.builder()
                .id(combo.getId())
                .precoTotal(combo.valorTotal())
                .produtos(combo.getProdutos().stream().map(ProdutoResponse::fromDomain).toList())
                .build();
    }
}
