package com.kyn.product.modules.cart.service.impl;

import com.kyn.product.modules.cart.dto.CartItem;
import com.kyn.product.modules.cart.dto.CartRequest;
import com.kyn.product.modules.cart.entity.Cart;

import reactor.core.publisher.Mono;

public interface CartService {

    Mono<Cart> createCart(CartRequest cartRequest);

    Mono<Cart> getCart(String email);

    Mono<Cart> addCartItem(String email, CartItem cartItem);

    Mono<Cart> updateCartItem(String email, CartItem cartItem);

    Mono<Cart> deleteCartItem(String email, String productId);    

    Mono<Void> clearCart(String email);
    
    
}

