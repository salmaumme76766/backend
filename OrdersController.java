package com.example.virtualplantstore.Controller;

import com.example.virtualplantstore.Entity.Orders;
import com.example.virtualplantstore.Entity.Products;
import com.example.virtualplantstore.Entity.User;
import com.example.virtualplantstore.Repository.OrdersRepo;
import com.example.virtualplantstore.Repository.ProductsRepo;
import com.example.virtualplantstore.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/Orders")
public class OrdersController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private OrdersRepo ordersRepo;

    @PostMapping("/CreateOrder/{userid}")
    public ResponseEntity<?> CreateOrder(@RequestBody Orders orders, @PathVariable String userid)
    {
        User user = userRepo.findByEmail(userid).orElseThrow(() -> new RuntimeException("user not found"));
         orders.setId(UUID.randomUUID().toString().split("-")[0].toUpperCase());
         orders.setUser(user);
        orders.setDate(LocalDate.now());
        orders.setStatus("placed");
        List<Products> products = new ArrayList<>();
        orders.getCart().forEach(cart -> {
            products.add(cart.getProducts());
        });
        orders.setProducts(products);
        Orders savedOrder = ordersRepo.save(orders);
        System.out.println(savedOrder);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/GetAllOrdersByUser/{userid}")
    public ResponseEntity<?> getAllOrdersByUser(@PathVariable String userid)
    {
        User user = userRepo.findByEmail(userid).orElseThrow(()-> new RuntimeException("user not found"));
        return new ResponseEntity<>(ordersRepo.findByUser(user), HttpStatus.OK);
    }

    @GetMapping("/GetAllOrders")
    public ResponseEntity<?> getAllOrders() {return new ResponseEntity<>(ordersRepo.findAll(),HttpStatus.OK);

    }

    @PutMapping("/UpdateStatus/{id}")
    public ResponseEntity<?> updateStatus(@RequestBody Orders orders,@PathVariable String id)
    {
        Orders order = ordersRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found "));
        if(order.getStatus().equals("cancelled"))
        {
            throw new RuntimeException("Order already cancelled");
        }
        else {
            order.setStatus(orders.getStatus());
            ordersRepo.save(order);
            return new ResponseEntity<>("Order "+ orders.getStatus() + " successfully", HttpStatus.OK);

        }
    }

    @PutMapping("AddRating/{id}/{rating}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @PathVariable Integer rating)
    {
        Orders order = ordersRepo.findById(id).orElseThrow(() -> new RuntimeException("order not found"));
        order.setRating(rating);
        ordersRepo.save(order);
        return new ResponseEntity<>("Order rated successfully", HttpStatus.OK);
    }
}
