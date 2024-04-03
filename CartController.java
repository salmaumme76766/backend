package com.example.virtualplantstore.Controller;

import com.example.virtualplantstore.Entity.Cart;
import com.example.virtualplantstore.Entity.Products;
import com.example.virtualplantstore.Entity.User;
import com.example.virtualplantstore.Repository.CartRepo;
import com.example.virtualplantstore.Repository.ProductsRepo;
import com.example.virtualplantstore.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/Cart")

public class CartController {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/AddUpdateCart/{userid}/{productid}")
    public ResponseEntity<?> createOrUpdateCard(@PathVariable String userid, @PathVariable Integer productid) {
        User user = userRepo.findByEmail(userid).orElseThrow(() -> new RuntimeException("User not found"));
        Products product = productsRepo.findById(productid).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<Cart> optionalCart = cartRepo.findByUserAndProducts(user, product);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setQuantity(cart.getQuantity() + 1); // Increased quantity
            cartRepo.save(cart);
            return new ResponseEntity<>("Added to cart", HttpStatus.OK);
        } else {
            Cart cart = new Cart();
            cart.setProducts(product);
            cart.setUser(user);
            cart.setQuantity(1);
            cartRepo.save(cart);

            return new ResponseEntity<>("Added to cart", HttpStatus.OK);
        }
    }

    @GetMapping("/GetCartByUser/{userid}")
    public ResponseEntity<?> GetCartByUser(@PathVariable String userid) {
        User user = userRepo.findByEmail(userid).orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseEntity<>(cartRepo.findByUser(user), HttpStatus.OK);
    }

    @PutMapping("/DecreaseProduct/{userid}/{productid}")
    public ResponseEntity<?> DecreaseProduct(@PathVariable String userid, @PathVariable Integer productid) {
        User user = userRepo.findByEmail(userid).orElseThrow(() -> new RuntimeException("User not found"));
        Products product = productsRepo.findById(productid).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<Cart> optionalCart = cartRepo.findByUserAndProducts(user, product);
        if (optionalCart.isPresent() && optionalCart.get().getQuantity() > 1) {
            Cart cart = optionalCart.get();
            cart.setQuantity(cart.getQuantity() - 1); // Decreased quantity
            cartRepo.save(cart);
            return new ResponseEntity<>("Quantity Decreased", HttpStatus.OK);
        } else {
            cartRepo.delete(optionalCart.get());
            return new ResponseEntity<>("Item deleted from cart", HttpStatus.OK);
        }
    }

    @DeleteMapping("/DeleteItem/{userid}/{productid}")
    public ResponseEntity<?> DeleteItem(@PathVariable String userid, @PathVariable Integer productid) {
        User user = userRepo.findByEmail(userid).orElseThrow(() -> new RuntimeException("User not found"));
        Products product = productsRepo.findById(productid).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<Cart> optionalCart = cartRepo.findByUserAndProducts(user, product);
        optionalCart.ifPresent(cartRepo::delete);
        return new ResponseEntity<>("Item deleted from cart", HttpStatus.OK);
    }

    @DeleteMapping("/DeleteCart/{userid}")
    public ResponseEntity<?> DeleteCart(@PathVariable String userid) {
        User user = userRepo.findByEmail(userid).orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart> cart = cartRepo.findByUser(user);
        cartRepo.deleteAll(cart);
        return new ResponseEntity<>("Cart cleared", HttpStatus.OK);
    }
}
