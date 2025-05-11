package com.kyn.product.base.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyn.product.modules.product.entity.ProductBasEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MongoInitConfig {

    private final ReactiveMongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;

    @Bean
    public CommandLineRunner initMongo() {
        return args -> {
            // initialize collections and load product.jsonl file
            initializeCollections()
                .then(loadProductData())
                .subscribe(
                    count -> log.info("load {} products.", count),
                    error -> log.error("error: {}", error.getMessage())
                );
        };
    }

    private Mono<Void> initializeCollections() {
        // PRODUCT_BAS collection initialization
        Mono<Void> productCollection = mongoTemplate.collectionExists("PRODUCT_BAS")
            .flatMap(exists -> {
                if (exists) {
                    log.info("PRODUCT_BAS collection exists. clear it.");
                    return mongoTemplate.dropCollection("PRODUCT_BAS")
                        .then(mongoTemplate.createCollection("PRODUCT_BAS", CollectionOptions.empty()));
                } else {
                    log.info("create PRODUCT_BAS collection.");
                    return mongoTemplate.createCollection("PRODUCT_BAS", CollectionOptions.empty());
                }
            }).then();

        // CART_BAS collection initialization
        Mono<Void> cartCollection = mongoTemplate.collectionExists("CART_BAS")
            .flatMap(exists -> {
                if (exists) {
                    log.info("CART_BAS collection exists. clear it.");
                    return mongoTemplate.dropCollection("CART_BAS")
                        .then(mongoTemplate.createCollection("CART_BAS", CollectionOptions.empty()));
                } else {
                    log.info("create CART_BAS collection.");
                    return mongoTemplate.createCollection("CART_BAS", CollectionOptions.empty());
                }
            }).then();

        // initialize two collections in parallel
        return Mono.when(productCollection, cartCollection);
    }

    private Mono<Integer> loadProductData() {
        log.info("load products from product.jsonl");
        
        try {
            ClassPathResource resource = new ClassPathResource("product.jsonl");
            Path filePath = Path.of(resource.getURI());
            
            return Flux.fromStream(Files.lines(filePath))
                .map(this::convertLineToProduct)
                .filter(product -> product != null)
                .flatMap(mongoTemplate::save)
                .count()
                .map(Long::intValue)
                .doFinally(signalType -> {
                    try {
                        Files.lines(filePath).close();
                    } catch (Exception e) {
                        log.error("Failed to close file stream", e);
                    }
                });
                
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            return Mono.just(0);
        }
    }
    
    /**
     * convert one line of JSONL file to ProductBasEntity
     */
    private ProductBasEntity convertLineToProduct(String line) {
        try {
            return mapJsonToProduct(objectMapper.readTree(line));
        } catch (Exception e) {
            log.error("Failed to parse line: {}", line, e);
            return null;
        }
    }
    
    /**
     * map JsonNode's fields to ProductBasEntity
     */
    private ProductBasEntity mapJsonToProduct(JsonNode jsonNode) {
        ProductBasEntity product = new ProductBasEntity();
        product.set_id(UUID.randomUUID().toString());
        if (jsonNode.has("Product Name")) {
            product.setProductName(jsonNode.get("Product Name").asText());
        }
        
        if (jsonNode.has("Category")) {
            product.setProductCategory(jsonNode.get("Category").asText());
        }
        
        if (jsonNode.has("Description")) {
            product.setProductDescription(jsonNode.get("Description").asText());
        }
        
        if (jsonNode.has("Product Specification")) {
            product.setProductSpecification(jsonNode.get("Product Specification").asText());
        }
        
        if (jsonNode.has("Selling Price")) {
            String price = String.valueOf(jsonNode.get("Selling Price").asDouble());
            product.setProductPrice(Double.parseDouble(price));
        }
        
        if (jsonNode.has("Image")) {
            product.setProductImage(jsonNode.get("Image").asText());
        }
        product.insertDocument("system");        
        return product;
    }
}
