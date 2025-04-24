package com.kyn.product.modules.cart.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.kyn.product.modules.cart.entity.Cart;

import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveMongoRepository<Cart,UUID>{
    Mono<Cart> findByEmail(String email);
    Mono<Void> deleteByEmail(String email);
}
