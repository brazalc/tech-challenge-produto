package br.com.fiap.techchallenge.application.usecases.combo;

import br.com.fiap.techchallenge.application.gateways.ComboGateway;
import br.com.fiap.techchallenge.domain.Combo;
import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ComboRequest;
import br.com.fiap.techchallenge.infrastructure.controllers.request.ProdutoRequest;

import java.util.List;
import java.util.UUID;

public class CriaComboInteractor {

    private final ComboGateway comboGateway;

    public CriaComboInteractor(final ComboGateway comboGateway) {
        this.comboGateway = comboGateway;
    }

    public Combo execute(ComboRequest comboRequest) {

        List<Produto> produtos = comboRequest.getProdutos().stream().map(ProdutoRequest::toDomain).toList();

        Combo combo = Combo.criaCombo(UUID.randomUUID(), produtos);

        return comboGateway.salva(combo);
    }
}