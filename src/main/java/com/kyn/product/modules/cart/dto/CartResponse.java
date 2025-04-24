package com.kyn.product.modules.cart.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private String _id;
    private String email;
    private List<CartItem> cartItems;
    private int totalPrice;
}
