package com.example.virtualplantstore.Repository;


import com.example.virtualplantstore.Entity.Cart;
import com.example.virtualplantstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
