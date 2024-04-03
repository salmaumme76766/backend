package com.example.virtualplantstore.Controller;

import com.example.virtualplantstore.Entity.Products;
import com.example.virtualplantstore.Repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class ProductsController {

    @Autowired
    private ProductsRepo productsRepo;

    @PostMapping("/AddProducts")
    public ResponseEntity<?>  addProducts(@RequestBody Products products)
    {
        products.setAvailability("In Stock");
        productsRepo.save(products);
        return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
    }

    @GetMapping("/GetAllProducts")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productsRepo.findAll(), HttpStatus.OK);
    }


    @DeleteMapping("/DeleteProducts/{id}")
    public ResponseEntity<?> deleteProducts(@PathVariable Integer id) {
        try {
            Products product = productsRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
            this.productsRepo.delete(product);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/UpdateProduct/{id}")
    public ResponseEntity<?> UpdateProduct(@PathVariable Integer id, @RequestBody Products obj) {
        Products product = productsRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(obj.getName());
        product.setPrice(obj.getPrice());
        product.setAvailability(obj.getAvailability());
        product.setDescription(obj.getDescription());
        product.setCategory(obj.getCategory());
        if (!obj.getImage().isEmpty()) {
            product.setImage(obj.getImage());
        }
        productsRepo.save(product);
        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    }

}
