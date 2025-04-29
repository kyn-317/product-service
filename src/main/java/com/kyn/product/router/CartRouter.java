package com.kyn.product.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.kyn.product.modules.cart.handler.CartHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CartRouter {
        

    private final CartHandler cartHandler;
    @Bean
    public RouterFunction<ServerResponse> cartRoutes() {
        return RouterFunctions.route(RequestPredicates.POST("/createCart"), this.cartHandler::createCart)
                .andRoute(RequestPredicates.POST("/getCart"), this.cartHandler::getCart)
                .andRoute(RequestPredicates.GET("/getCartByEmail/{email}"), this.cartHandler::getCartByEmail)
                .andRoute(RequestPredicates.POST("/addCartItem"), this.cartHandler::addCartItem)
                .andRoute(RequestPredicates.POST("/updateCartItem"), this.cartHandler::updateCartItem)
                .andRoute(RequestPredicates.POST("/deleteCartItem"), this.cartHandler::deleteCartItem)
                .andRoute(RequestPredicates.POST("/clearCart"), this.cartHandler::clearCart);

    }
}
