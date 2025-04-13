package com.kyn.product.modules.cart.dto;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Builder
public class CartItemRequest {
    private ObjectId productId;
    private String productName;
    private String productPrice;
    private int productQuantity;
}
