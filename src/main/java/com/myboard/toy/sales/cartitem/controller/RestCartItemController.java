package com.myboard.toy.sales.cartitem.controller;

import com.myboard.toy.sales.cart.service.CartService;
import com.myboard.toy.sales.cartitem.service.CartItemService;
import com.myboard.toy.sales.domain.Cart;
import com.myboard.toy.sales.domain.dto.CartItemUpdateAmountRequestForm;
import com.myboard.toy.security.domain.entity.Account;
import com.myboard.toy.security.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class RestCartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    /*
     UPDATE
     */

    /* 증가 */
    @PostMapping("/increaseItem")
    public ResponseEntity<Map<String, String>> increaseAmountWhenPushTheButton(
            Principal principal,
            @RequestBody CartItemUpdateAmountRequestForm form
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            Account account = userService.getAccountByPrincipal((UsernamePasswordAuthenticationToken) principal);
            Cart cart = cartService.findCartByAccount(account);

            form.setAmount(form.getAmount()); // 수량 증가

            cartItemService.updateCartItemAmount(cart, form);

            response.put("message", "Item quantity increased successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Failed to update item quantity.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /* 감소 */
    @PostMapping("/decreaseItem")
    public ResponseEntity<Map<String, String>> decreaseAmountWhenPushTheButton(
            Principal principal,
            @RequestBody CartItemUpdateAmountRequestForm form
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            Account account = userService.getAccountByPrincipal((UsernamePasswordAuthenticationToken) principal);
            Cart cart = cartService.findCartByAccount(account);

            form.setAmount(Math.max(0, form.getAmount())); // 수량 감소

            cartItemService.updateCartItemAmount(cart, form);

            response.put("message", "Item quantity decreased successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Failed to update item quantity.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    /*

        REMOVE

    */
    /*
    @PostMapping("/removeItem")
    public ResponseEntity<Map<String, String>> removeItemFromCart(
            Principal principal,
            @RequestBody CartItemRemoveRequestForm form
    ) {
        Map<String, String> response = new HashMap<>();
        try {
            Account account = accountUtils.getAccountByPrincipal((UsernamePasswordAuthenticationToken) principal);


            form.setCartId(account.getCart().getId());

            cartItemService.removeCartItem(form);

            response.put("message", "Item removed successfully.");
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Failed to remove item.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    */

}