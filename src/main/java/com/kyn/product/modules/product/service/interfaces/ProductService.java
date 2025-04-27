package com.kyn.product.modules.product.service.interfaces;

import java.util.List;

import com.kyn.product.modules.product.dto.ProductBasDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductBasDto> findAll();
    Mono<ProductBasDto> findById(String id);
    Mono<Long> countAll();
    Flux<ProductBasDto> findbyProductPaging(int page, int size);
    Flux<ProductBasDto> getProductsByIds(List<String> ids);
}
