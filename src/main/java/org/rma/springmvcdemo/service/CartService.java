package org.rma.springmvcdemo.service;

import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.CartItem;
import org.rma.springmvcdemo.model.Product;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.repository.CartRepository;
import org.rma.springmvcdemo.repository.ProductRepository;
import org.rma.springmvcdemo.repository.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopperRepository shopperRepository;

    public Cart getCartByShopperId(Long shopperId) {
        Shopper shopper = shopperRepository.findById(shopperId)
                .orElseThrow(() -> new RuntimeException("Shopper not found"));
        return shopper.getCart();
    }

    public Cart addItemToCart(Long shopperId, Long productId, int quantity) {
        Cart cart = getCartByShopperId(shopperId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Update quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice()); // Assuming Product has a price field
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long shopperId, Long productId) {
        Cart cart = getCartByShopperId(shopperId);

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(Long shopperId, Long productId, int newQuantity) {
        Cart cart = getCartByShopperId(shopperId);

        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(newQuantity));

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    private void updateCartTotals(Cart cart) {
        cart.calculateTotalPrice();
    }

    public double calculateEstimatedTaxes(Cart cart, double taxRate) {
        return cart.getTotalPrice() * taxRate;
    }
}