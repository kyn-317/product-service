package com.kyn.product.base.dto;

import org.springframework.http.HttpStatus;

import com.kyn.product.base.exception.GeneralException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private String code;

    public static ErrorResponse from(GeneralException ex) {
        return create(ex.getMessage(), ex.getStatus(), ex.getStatus().name());
    }
}