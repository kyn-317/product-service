package com.kyn.product.modules.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Builder
public class CartItem {
    private String productId;
    private String productName;
    private Double productPrice;
    private String productImage;
    private int productQuantity;
}
