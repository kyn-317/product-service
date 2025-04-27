package com.kyn.product.modules.cart.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kyn.product.modules.cart.dto.CartItem;
import com.kyn.product.modules.cart.dto.CartItemRequest;
import com.kyn.product.modules.cart.dto.CartRequest;
import com.kyn.product.modules.cart.mapper.CartEntityDtoMapper;
import com.kyn.product.modules.cart.service.impl.CartService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartHandler {
    
    private final CartService cartService;
    public Mono<ServerResponse> createCart(ServerRequest request){

        return request.bodyToMono(CartRequest.class)
        .flatMap(cartService::createCart)
        .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }

    public Mono<ServerResponse> getCart(ServerRequest request){
        return request.bodyToMono(CartRequest.class)
        .map(CartRequest::getEmail)
        .flatMap(cartService::getCart)
        .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }

    public Mono<ServerResponse> addCartItem(ServerRequest request){
        return request.bodyToMono(CartRequest.class)
        .flatMap(item -> cartService.addCartItem(item.getEmail(),
        CartEntityDtoMapper.cartItemRequestToCartItem(item.getCartItems().get(0))))
        .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }

    public Mono<ServerResponse> updateCartItem(ServerRequest request){
        return request.bodyToMono(CartRequest.class)
        .flatMap(item -> cartService.updateCartItem(item.getEmail(),
        CartEntityDtoMapper.cartItemRequestToCartItem(item.getCartItems().get(0))))
        .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }
    public Mono<ServerResponse> deleteCartItem(ServerRequest request){
        return request.bodyToMono(CartRequest.class)
        .flatMap(item -> cartService.deleteCartItem(item.getEmail()
                    , item.getCartItems().get(0).getProductId()))
        .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }
    
    public Mono<ServerResponse> clearCart(ServerRequest request){
        return request.bodyToMono(CartRequest.class)
        .map(CartRequest::getEmail)
        .flatMap(cartService::clearCart)
        .flatMap(cart -> ServerResponse.ok().bodyValue(cart));
    }

    
}