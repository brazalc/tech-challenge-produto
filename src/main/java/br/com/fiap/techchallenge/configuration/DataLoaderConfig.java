package br.com.fiap.techchallenge.configuration;

import br.com.fiap.techchallenge.domain.Produto;
import br.com.fiap.techchallenge.infrastructure.persistence.entity.ComboEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.entity.ProdutoEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.repository.ComboRepository;
import br.com.fiap.techchallenge.infrastructure.persistence.repository.ProdutoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class DataLoaderConfig {

    @Autowired
    private ComboRepository comboRepository;

    @Bean
    ApplicationRunner init() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<ComboEntity> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");

            try {
                ComboEntity combo = mapper.readValue(inputStream, typeReference);
                comboRepository.save(combo);
                System.out.println("Combo saved to MongoDB");
            } catch (IOException e) {
                System.out.println("Unable to save combo: " + e.getMessage());
            }
        };
    }
}
