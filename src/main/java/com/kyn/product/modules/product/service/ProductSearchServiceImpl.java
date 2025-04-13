package com.kyn.product.modules.product.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.redisson.api.RMapCacheReactive;

import org.redisson.api.RedissonClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kyn.product.base.util.MongoDbUtil;
import com.kyn.product.modules.product.dto.ProductBasDto;
import com.kyn.product.modules.product.mapper.ProductEntityDtoMapper;
import com.kyn.product.modules.product.repository.ProductBasRepository;
import com.kyn.product.modules.product.service.interfaces.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductSearchServiceImpl implements ProductService {

    private final ProductBasRepository productBasRepository;

    private final RMapCacheReactive<String, ProductBasDto> productBasMap;

    private static final int CACHE_TTL = 1;

    public ProductSearchServiceImpl(ProductBasRepository productBasRepository, RedissonClient redissonClient) {
        this.productBasRepository = productBasRepository;
        this.productBasMap = redissonClient.reactive().getMapCache("product:id");
    }

    @Override
    public Flux<ProductBasDto> findAll() {
        return productBasRepository.findAll().map(ProductEntityDtoMapper::entityToDto);
    }

    @Override
    public Mono<ProductBasDto> findById(String id) {
        return this.productBasMap.get(id)
                .switchIfEmpty(
                        productBasRepository.findById(MongoDbUtil.toObjectId(id))
                                .map(ProductEntityDtoMapper::entityToDto)
                                .flatMap(dto -> this.productBasMap.fastPut(id, dto, CACHE_TTL, TimeUnit.MINUTES)
                                        .thenReturn(dto)));
    }

    @Override
    public Flux<ProductBasDto> findbyProductPaging(int page, int size) {
        return productBasRepository.findAllBy(PageRequest.of(page, size)).map(ProductEntityDtoMapper::entityToDto);
    }

    @Override
        public Flux<ProductBasDto> getProductsByIds(List<ObjectId> ids) {
        return productBasRepository.findAllByIdIn(ids)
                .map(ProductEntityDtoMapper::entityToDto);
    }
}
