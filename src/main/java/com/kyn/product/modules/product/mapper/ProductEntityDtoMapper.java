package com.kyn.product.modules.product.mapper;
import org.springframework.beans.BeanUtils;

import com.kyn.product.modules.product.dto.ProductBasDto;
import com.kyn.product.modules.product.entity.ProductBasEntity;
public class ProductEntityDtoMapper {
    public static ProductBasDto entityToDto(ProductBasEntity entity) {
        ProductBasDto dto = new ProductBasDto();
        BeanUtils.copyProperties(entity, dto);
        dto.set_id(entity.get_id());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDt(entity.getCreatedDt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDt(entity.getUpdatedDt());
        return dto;
    }

    public static ProductBasEntity dtoToEntity(ProductBasDto dto) {
        ProductBasEntity entity = new ProductBasEntity();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }
}
