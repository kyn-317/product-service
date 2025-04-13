package com.kyn.product.modules.product.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String productPrice;

    private String productImage;

    private LocalDateTime regDt;

    private String regrId;

    private LocalDateTime amdDt;

    private String amdrId;

}
