package br.com.fiap.techchallenge.beans;

import br.com.fiap.techchallenge.application.gateways.ComboGateway;
import br.com.fiap.techchallenge.application.gateways.ProdutoGateway;
import br.com.fiap.techchallenge.application.usecases.combo.CriaComboInteractor;
import br.com.fiap.techchallenge.application.usecases.combo.ListaComboInteractor;
import br.com.fiap.techchallenge.infrastructure.controllers.mappers.ComboDtoMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.gateways.ComboRepositoryGateway;
import br.com.fiap.techchallenge.infrastructure.persistence.gateways.ProdutoRepositoryGateway;
import br.com.fiap.techchallenge.infrastructure.persistence.repository.ComboRepository;
import br.com.fiap.techchallenge.infrastructure.persistence.repository.ProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComboConfig {

    @Bean
    ProdutoGateway produtoGateway(ProdutoRepository produtoRepository) {
        return new ProdutoRepositoryGateway(produtoRepository);
    }

    @Bean
    CriaComboInteractor criaComboInteractor(ComboGateway comboGateway, ProdutoGateway produtoGateway) {
        return new CriaComboInteractor(comboGateway, produtoGateway);
    }

    @Bean
    ListaComboInteractor listaComboInteractor(ComboGateway comboGateway) {
        return new ListaComboInteractor(comboGateway);
    }

    @Bean
    ComboGateway comboGateway(ProdutoRepository produtoRepository, ComboRepository comboRepository) {
        return new ComboRepositoryGateway(produtoRepository, comboRepository);
    }

    @Bean
    ComboDtoMapper comboDtoMapper() {
        return new ComboDtoMapper();
    }
}