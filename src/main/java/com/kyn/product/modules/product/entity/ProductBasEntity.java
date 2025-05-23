package com.kyn.product.modules.product.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.kyn.product.base.entity.BaseDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(staticName = "create")
@Document("PRODUCT_BAS")
public class ProductBasEntity extends BaseDocuments {

    @Id
    private String _id;

    @Field("PRODUCT_NAME")
    private String productName;

    @Field("PRODUCT_CATEGORY")
    private String productCategory;

    @Field("PRODUCT_DESCRIPTION")
    private String productDescription;

    @Field("PRODUCT_SPECIFICATION")
    private String productSpecification;

    @Field("PRODUCT_PRICE")
    private Double productPrice;

    @Field("PRODUCT_IMAGE")
    private String productImage;

}
