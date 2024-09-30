package org.rma.springmvcdemo.controller;

import org.rma.springmvcdemo.model.Cart;
import org.rma.springmvcdemo.model.Product;
import org.rma.springmvcdemo.model.Shopper;
import org.rma.springmvcdemo.service.CartService;
import org.rma.springmvcdemo.service.ProductService;
import org.rma.springmvcdemo.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopperService shopperService;

    @GetMapping
    public String viewCart(Model model) {
        Shopper shopper = getAuthenticatedShopper();
        Cart cart = cartService.getCartByShopperId(shopper.getId());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        Shopper shopper = getAuthenticatedShopper();
        cartService.addItemToCart(shopper.getId(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        Shopper shopper = getAuthenticatedShopper();
        cartService.removeItemFromCart(shopper.getId(), productId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItemQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        Shopper shopper = getAuthenticatedShopper();
        cartService.updateItemQuantity(shopper.getId(), productId, quantity);
        return "redirect:/cart";
    }

    private Shopper getAuthenticatedShopper() {

        return shopperService.getShopperById(2L);  // Using shopper1 with ID 1
    }

}