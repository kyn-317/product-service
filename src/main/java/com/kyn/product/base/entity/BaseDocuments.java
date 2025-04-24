package com.kyn.product.base.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDocuments {

    @Field("CREATED_BY")
    private String createdBy;

    @Field("CREATED_AT")
    private LocalDateTime createdAt;

    @Field("UPDATED_BY")
    private String updatedBy;

    @Field("UPDATED_AT")
    private LocalDateTime updatedAt;

    public void insertDocument(String id) {
        this.createdBy = id;
        this.createdAt = LocalDateTime.now();
        this.updatedBy = id;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDocument(String id) {
        this.updatedBy = id;
        this.updatedAt = LocalDateTime.now();
    }
}
