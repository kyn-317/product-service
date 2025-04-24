package com.kyn.product;

import com.kyn.product.modules.cart.dto.CartItem;
import com.kyn.product.modules.cart.dto.CartItemRequest;
import com.kyn.product.modules.cart.dto.CartRequest;
import com.kyn.product.modules.cart.dto.CartResponse;
import com.kyn.product.modules.cart.service.impl.CartService;
import com.kyn.product.modules.product.dto.ProductBasDto;
import com.kyn.product.modules.product.service.interfaces.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class CartTest {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CartService cartService;
    
    private List<ProductBasDto> randomProducts;
    private static final String TEST_EMAIL = "test@example.com";
    private static final Random random = new Random();
    
    @BeforeEach
    void setUp() {
        // Clear existing cart data before each test
        cartService.clearCart(TEST_EMAIL).block();
        
        // Load random products
        randomProducts = loadRandomProducts(3).collectList().block();
    }
    
    /**
     * Method to fetch N random products
     */
    private Flux<ProductBasDto> loadRandomProducts(int count) {
        return productService.findAll()
                .collectList()
                .flatMapMany(products -> {
                    List<ProductBasDto> selectedProducts = new ArrayList<>();
                    int totalProducts = products.size();
                    
                    if (totalProducts == 0) {
                        return Flux.empty();
                    }
                    
                    // Randomly select products
                    for (int i = 0; i < Math.min(count, totalProducts); i++) {
                        int randomIndex = random.nextInt(totalProducts);
                        selectedProducts.add(products.get(randomIndex));
                    }
                    
                    return Flux.fromIterable(selectedProducts);
                });
    }
    
    @Test
    @DisplayName("Create cart test")
    void createCartTest() {
        // Create cart item requests from random products
        List<CartItemRequest> cartItemRequests = new ArrayList<>();
        
        for (ProductBasDto product : randomProducts) {
            CartItemRequest itemRequest = CartItemRequest.builder()
                    .productId(product.get_id())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQuantity(random.nextInt(5) + 1) // Random quantity between 1-5
                    .build();
            cartItemRequests.add(itemRequest);
        }
        
        CartRequest cartRequest = CartRequest.builder()
                .email(TEST_EMAIL)
                .cartItems(cartItemRequests)
                .build();
        
        // Create cart and verify
        StepVerifier.create(cartService.createCart(cartRequest))
                .assertNext(cartResponse -> {
                    assert cartResponse.getEmail().equals(TEST_EMAIL);
                    assert cartResponse.getCartItems().size() == randomProducts.size();
                    assert cartResponse.getTotalPrice() > 0;
                })
                .verifyComplete();
    }
    
    @Test
    @DisplayName("Add item to cart test")
    void addCartItemTest() {
        // First create a cart
        createCartWithOneItem();
        
        // Get a new product (not in current cart)
        ProductBasDto newProduct = loadRandomProducts(1)
                .collectList()
                .block()
                .get(0);
        
        // Create cart item
        CartItem cartItem = CartItem.builder()
                .productId(newProduct.get_id())
                .productName(newProduct.getProductName())
                .productPrice(newProduct.getProductPrice())
                .productImage(newProduct.getProductImage())
                .productQuantity(2)
                .build();
        
        // Add item and verify
        StepVerifier.create(cartService.addCartItem(TEST_EMAIL, cartItem))
                .assertNext(cartResponse -> {
                    assert cartResponse.getCartItems().size() == 2;
                    assert cartResponse.getCartItems().stream()
                            .anyMatch(item -> item.getProductId().equals(newProduct.get_id()));
                })
                .verifyComplete();
    }
    
    @Test
    @DisplayName("Update cart item quantity test")
    void updateCartItemTest() {
        // First create a cart
        CartResponse cart = createCartWithOneItem();
        
        // Change quantity of the first item
        CartItem originalItem = cart.getCartItems().get(0);
        CartItem updatedItem = CartItem.builder()
                .productId(originalItem.getProductId())
                .productName(originalItem.getProductName())
                .productPrice(originalItem.getProductPrice())
                .productImage(originalItem.getProductImage())
                .productQuantity(10) // Change quantity to 10
                .build();
        
        // Update item and verify
        StepVerifier.create(cartService.updateCartItem(TEST_EMAIL, updatedItem))
                .assertNext(cartResponse -> {
                    CartItem item = cartResponse.getCartItems().get(0);
                    assert item.getProductQuantity() == 10;
                    assert cartResponse.getTotalPrice() == (item.getProductPrice() * 10);
                })
                .verifyComplete();
    }
    
    @Test
    @DisplayName("Delete cart item test")
    void deleteCartItemTest() {
        // First create a cart
        CartResponse cart = createCartWithOneItem();
        
        String productId = cart.getCartItems().get(0).getProductId();
        
        // Delete item and verify
        StepVerifier.create(cartService.deleteCartItem(TEST_EMAIL, productId))
                .assertNext(cartResponse -> {
                    assert cartResponse.getCartItems().isEmpty();
                    assert cartResponse.getTotalPrice() == 0;
                })
                .verifyComplete();
    }
    
    @Test
    @DisplayName("Clear cart test")
    void clearCartTest() {
        // First create a cart
        createCartWithOneItem();
        
        // Clear cart and verify
        StepVerifier.create(cartService.clearCart(TEST_EMAIL)
                .then(cartService.getCart(TEST_EMAIL)))
                .expectComplete() // Empty Mono is returned when cart is not found
                .verify();
                
        // Double check that cart is really empty
        StepVerifier.create(cartService.getCart(TEST_EMAIL))
                .verifyComplete(); // Expect empty Mono
    }
    
    /**
     * Helper method to create a test cart with one item
     */
    private CartResponse createCartWithOneItem() {
        ProductBasDto product = randomProducts.get(0);
        
        CartItemRequest itemRequest = CartItemRequest.builder()
                .productId(product.get_id())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productQuantity(1)
                .build();
        
        List<CartItemRequest> cartItemRequests = new ArrayList<>();
        cartItemRequests.add(itemRequest);
        
        CartRequest cartRequest = CartRequest.builder()
                .email(TEST_EMAIL)
                .cartItems(cartItemRequests)
                .build();
        
        return cartService.createCart(cartRequest).block();
    }
}
