package com.kyn.product.modules.cart.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kyn.product.modules.cart.dto.CartItem;
import com.kyn.product.modules.cart.dto.CartItemRequest;
import com.kyn.product.modules.cart.dto.CartRequest;
import com.kyn.product.modules.cart.dto.CartResponse;
import com.kyn.product.modules.cart.entity.Cart;
import com.kyn.product.modules.product.dto.ProductBasDto;

public class CartEntityDtoMapper {
    
    public static Cart createCart (String email, List<CartItemRequest> requestItems){
        var cartItems = requestItems.stream()
        .map(item -> CartItem.builder()
        .productId(item.getProductId())
        .productName(item.getProductName())
        .productPrice(item.getProductPrice())
        .productQuantity(item.getProductQuantity())
        .build())
        .collect(Collectors.toList());
        return Cart.builder()
                ._id(UUID.randomUUID().toString())
                .email(email)
                .cartItems(cartItems)
                .build();
    }

    public static CartItem productToCartItem(ProductBasDto product, CartItemRequest request){
        return CartItem.builder()
        .productId(product.get_id())
        .productName(product.getProductName())
        .productPrice(product.getProductPrice())
        .productQuantity(request.getProductQuantity())
        .build();
    }
    public static Cart createCartFromProducts(List<ProductBasDto> products, CartRequest cartRequest) {
        List<CartItem> cartItems = cartRequest.getCartItems()
            .stream()
            .map(itemRequest -> {
                var product = products.stream()
                    .filter(p -> p.get_id().equals(itemRequest.getProductId().toString()))
                    .findFirst().orElse(null);
                if (product != null) return productToCartItem(product, itemRequest);
                return null;
            })
            .filter(item -> item != null)
            .collect(Collectors.toList());
        
        Cart cart=Cart.builder()
        ._id(UUID.randomUUID().toString())
        .email(cartRequest.getEmail())
        .cartItems(cartItems)
        .totalPrice(cartItems.stream()
        .mapToDouble(item -> item.getProductPrice() * item.getProductQuantity())
        .sum())
        .build();
        cart.insertDocument(cartRequest.getEmail());
        return cart;
    }

    public static CartResponse cartToCartResponse(Cart cart){
        return CartResponse.builder()
        ._id(cart.get_id())
        .email(cart.getEmail())
        .cartItems(cart.getCartItems())
        .totalPrice(cart.getTotalPrice())
        .build();

        
    }
}
