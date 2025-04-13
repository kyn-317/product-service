package com.kyn.product.modules.cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.kyn.product.modules.cart.dto.CartItem;
import com.kyn.product.modules.cart.dto.CartItemRequest;
import com.kyn.product.modules.cart.dto.CartRequest;
import com.kyn.product.modules.cart.entity.Cart;
import com.kyn.product.modules.cart.mapper.CartEntityDtoMapper;
import com.kyn.product.modules.cart.repository.CartRepository;
import com.kyn.product.modules.cart.service.impl.CartService;
import com.kyn.product.modules.product.dto.ProductBasDto;
import com.kyn.product.modules.product.service.interfaces.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl  implements CartService{


    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Mono<Cart> createCart(CartRequest cartRequest) {                   
        return productService.getProductsByIds(
                cartRequest.getCartItems().stream()
                    .map(CartItemRequest::getProductId)
                    .collect(Collectors.toList())).collectList()
                .map(productsList -> CartEntityDtoMapper
                                    .createCartFromProducts(productsList, cartRequest));
    }

    @Override
    public Mono<Cart> getCart(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCart'");
    }

    @Override
    public Mono<Cart> addCartItem(String userId, CartItem cartItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCartItem'");
    }

    @Override
    public Mono<Cart> updateCartItem(String userId, CartItem cartItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCartItem'");
    }

    @Override
    public Mono<Cart> deleteCartItem(String userId, String productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCartItem'");
    }

    @Override
    public Mono<Cart> clearCart(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearCart'");
    }
    
}
