package com.kyn.product.modules.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.kyn.product.modules.product.entity.ProductBasEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductBasRepository extends ReactiveMongoRepository<ProductBasEntity, String> {
    Flux<ProductBasEntity> findAllBy(Pageable pageable);

    Flux<ProductBasEntity> findAllBy_idIn(List<String> ids);

    Mono<ProductBasEntity> findBy_id(String id);

    @Query(value = "{}", count = true)
    Mono<Long> countAll();
}
