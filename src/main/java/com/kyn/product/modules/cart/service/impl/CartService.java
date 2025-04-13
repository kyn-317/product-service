package com.kyn.product.modules.cart.service.impl;

import com.kyn.product.modules.cart.dto.CartItem;
import com.kyn.product.modules.cart.dto.CartRequest;
import com.kyn.product.modules.cart.dto.CartResponse;
import com.kyn.product.modules.cart.entity.Cart;

import reactor.core.publisher.Mono;

public interface CartService {

    Mono<CartResponse> createCart(CartRequest cartRequest);

    Mono<CartResponse> getCart(String email);

    Mono<CartResponse> addCartItem(String email, CartItem cartItem);

    Mono<CartResponse> updateCartItem(String email, CartItem cartItem);

    Mono<CartResponse> deleteCartItem(String email, String productId);    

    Mono<Void> clearCart(String email);
    
    
}

