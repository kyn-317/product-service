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
import com.kyn.product.modules.product.service.interfaces.ProductService;

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
                                    .createCartFromProducts(productsList, cartRequest))
                                    .flatMap(cartRepository::save);
    }

    @Override
    public Mono<Cart> getCart(String email) {
        return cartRepository.findByEmail(email);
    }

    @Override
    public Mono<Cart> addCartItem(String email, CartItem cartItem) {
        return cartRepository.findByEmail(email)
        .map(cart -> addToCart(cart, cartItem))
        .flatMap(cartRepository::save);
    }

    private Cart addToCart(Cart cart, CartItem cartItem){
        cart.getCartItems().add(cartItem);
        return cart;
    }

    @Override
    public Mono<Cart> updateCartItem(String email, CartItem cartItem) {
        return cartRepository.findByEmail(email)
            .map(cart -> updateCartItem(cart, cartItem))
            .flatMap(cartRepository::save);
    }

    private Cart updateCartItem(Cart cart, CartItem cartItem){
        List<CartItem> updatedItems = cart.getCartItems().stream()
        .map(item -> (item.getProductId().equals(cartItem.getProductId()))? 
                cartItem : item)
        .collect(Collectors.toList());
        cart.setCartItems(updatedItems);
        cart.setTotalPrice(totalPrice(updatedItems));
        return cart;

    }

    @Override
    public Mono<Cart> deleteCartItem(String email, String productId) {
        return cartRepository.findByEmail(email)
        .map(cart -> deleteCartItem(cart, productId))
        .flatMap(cartRepository::save);
    }

    private Cart deleteCartItem(Cart cart, String productId){
        List<CartItem> updatedItems = cart.getCartItems().stream()
        .filter(item -> !item.getProductId().equals(productId))
        .collect(Collectors.toList());

        cart.setCartItems(updatedItems);
        cart.setTotalPrice(totalPrice(updatedItems));
        return cart; 
    }

    @Override
    public Mono<Void> clearCart(String email) {
        return cartRepository.deleteByEmail(email);
    }

    private int totalPrice(List<CartItem> cartItems){
        return cartItems.stream()
        .mapToInt(item -> Integer.parseInt(item.getProductPrice()) * item.getProductQuantity())
        .sum();
    }
    
}
