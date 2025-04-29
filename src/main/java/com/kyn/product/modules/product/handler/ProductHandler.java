package com.kyn.product.modules.product.handler;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kyn.product.modules.product.dto.ProductBasDto;
import com.kyn.product.modules.product.mapper.ResponseDtoMapper;
import com.kyn.product.modules.product.service.interfaces.ProductService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ProductHandler {

    private final ProductService productService;
    
    public ProductHandler(ProductService productService) {
        this.productService = productService;   
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().body(productService.findAll(), ProductBasDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        log.info("pathVariable: {}", request.pathVariable("id"));
        return ServerResponse.ok().body(productService.findById(request.pathVariable("id")), ProductBasDto.class);
    }

    public Mono<ServerResponse> findbyProductPaging(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        
        return Mono.zip(
            productService.findbyProductPaging(page, size).collectList(),
            productService.countAll()
        ).map(tuple -> {
            return  ResponseDtoMapper.toProductPageResponse(
                tuple.getT1(), tuple.getT2(), size, page);
        }).flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> findByIds(ServerRequest request) {
       log.info("findByIds");
       return request.bodyToMono(new ParameterizedTypeReference<List<String>>() {})
        .flatMap(ids -> ServerResponse.ok().body(productService.getProductsByIds(ids), ProductBasDto.class))
        .doOnError(error -> log.error("Error: {}", error));
    }
}
