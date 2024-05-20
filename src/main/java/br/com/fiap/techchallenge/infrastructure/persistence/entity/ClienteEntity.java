package br.com.fiap.techchallenge.infrastructure.persistence.entity;

import br.com.fiap.techchallenge.domain.Cliente;
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
@Document(collection = "Clientes")
public class ClienteEntity {

    private String cpf;

    public static ClienteEntity criaEntity(Cliente cliente) {
        return new ClienteEntity(cliente.getCpf());
    }

    public Cliente toDomain() {
        return Cliente.criaCliente(this.cpf);
    }
}
