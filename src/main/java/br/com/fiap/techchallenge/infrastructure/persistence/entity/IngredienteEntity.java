package br.com.fiap.techchallenge.infrastructure.persistence.entity;

import br.com.fiap.techchallenge.domain.Ingrediente;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Ingredientes")
public class IngredienteEntity {

    private UUID id;
    private String descricao;

    public static IngredienteEntity criaEntity(Ingrediente ingrediente) {
        return new IngredienteEntity(ingrediente.getId(), ingrediente.getDescricao());
    }

    public Ingrediente toDomain() {
        return Ingrediente.criaIngrediente(this.id, this.descricao);
    }
}
