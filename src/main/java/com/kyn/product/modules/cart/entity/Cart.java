package com.kyn.product.modules.cart.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.kyn.product.base.entity.BaseDocuments;
import com.kyn.product.modules.cart.dto.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("CART_BAS")
public class Cart extends BaseDocuments{

    @Id
    private ObjectId _id;

    @Field("EMAIL")
    private String email;
    @Field("CART_ITEMS")
    private List<CartItem> cartItems;

    @Field("TOTAL_PRICE")
    private int totalPrice;
    
    
    
}