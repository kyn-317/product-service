package com.kyn.product.modules.product.mapper;

import java.util.List;

import com.kyn.product.modules.product.dto.ProductBasDto;
import com.kyn.product.modules.product.dto.ProductPageResponse;

public class ResponseDtoMapper {
    

    
    public static ProductPageResponse toProductPageResponse(List<ProductBasDto> content, long totalCount, int size ,int page) {
            var totalPages = (int) Math.ceil((double) totalCount / size);
            return ProductPageResponse.builder()
            .content(content)
            .totalElements(totalCount)
            .totalPages(totalPages)
            .size(size)
            .number(page)
            .first(page == 0)
            .last(page >= totalPages - 1)
            .empty(content.isEmpty())
            .build();            
    }
}
