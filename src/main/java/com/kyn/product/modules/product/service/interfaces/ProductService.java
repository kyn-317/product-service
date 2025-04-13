package com.kyn.product.modules.product.service.interfaces;

import java.util.List;

import org.bson.types.ObjectId;

import com.kyn.product.modules.product.dto.ProductBasDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductBasDto> findAll();
    Mono<ProductBasDto> findById(String id);
    Flux<ProductBasDto> findbyProductPaging(int page, int size);
    Flux<ProductBasDto> getProductsByIds(List<ObjectId> ids);
}
