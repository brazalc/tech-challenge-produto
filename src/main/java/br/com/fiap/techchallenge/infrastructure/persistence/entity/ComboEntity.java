package br.com.fiap.techchallenge.infrastructure.persistence.entity;

import br.com.fiap.techchallenge.domain.Combo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Combos")
public class ComboEntity {

    private UUID id;

    private List<ProdutoEntity> produtos;

    public static ComboEntity criaComboEntity(Combo combo) {

        List<ProdutoEntity> produtos = combo.getProdutos().stream()
                .map(ProdutoEntity::toEntity)
                .toList();

        return new ComboEntity(combo.getId(), produtos);
    }

    public Combo toDomain() {
        return Combo.criaCombo(id, produtos.stream().map(ProdutoEntity::toDomain).toList());
    }
}
