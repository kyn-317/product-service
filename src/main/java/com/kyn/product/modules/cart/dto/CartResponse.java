package com.kyn.product.modules.cart.dto;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private ObjectId _id;
    private String email;
    private List<CartItem> cartItems;
    private int totalPrice;
}
