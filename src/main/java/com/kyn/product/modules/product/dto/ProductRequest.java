package com.kyn.product.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Builder
public class ProductRequest {
    
    private String productName;

    private String productCategory;

    private String productDescription;

    private String productSpecification;

    private String productPrice;

    private String productImage;
}
