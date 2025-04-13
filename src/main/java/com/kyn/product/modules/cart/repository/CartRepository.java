package com.kyn.product.modules.cart.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.kyn.product.modules.cart.entity.Cart;

import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveMongoRepository<Cart,ObjectId>{
    
}
