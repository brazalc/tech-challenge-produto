package br.com.fiap.techchallenge.infrastructure.controllers.request;

import br.com.fiap.techchallenge.domain.Ingrediente;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredienteRequest {

    private String descricao;

    public Ingrediente toDomain() {
        return Ingrediente.criaIngrediente(UUID.randomUUID(), this.descricao);
    }
}
