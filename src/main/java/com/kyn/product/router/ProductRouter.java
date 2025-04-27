package com.kyn.product.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kyn.product.modules.product.handler.ProductHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProductRouter {
    

    private final ProductHandler productHandler;
    @Bean
    public RouterFunction<ServerResponse> productRoutes() {
        return RouterFunctions.route(RequestPredicates.GET("/findAllProduct"), this.productHandler::findAll)
                .andRoute(RequestPredicates.GET("/findProductById/{id}"), this.productHandler::findById)
                .andRoute(RequestPredicates.GET("/findProductPaging"), this.productHandler::findbyProductPaging);

    }
}
