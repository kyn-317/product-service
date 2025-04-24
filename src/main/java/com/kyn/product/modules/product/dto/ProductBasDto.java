package com.kyn.product.modules.product.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductBasDto {

    private String _id;

    private String productName;

    private String productCategory;

    private String productDescription;

    private String productSpecification;

    private int productPrice;

    private String productImage;

    private LocalDateTime createdDt;

    private String createdBy;

    private LocalDateTime updatedDt;

    private String updatedBy;

}
