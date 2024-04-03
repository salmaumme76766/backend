package com.example.virtualplantstore.Repository;

import com.example.virtualplantstore.Entity.Cart;
import com.example.virtualplantstore.Entity.Products;
import com.example.virtualplantstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    Optional<Cart> findByUserAndProducts(User user, Products product);

    List<Cart> findByUser(User user);

    @Override
    void deleteAll(Iterable<? extends Cart> entities);
}
