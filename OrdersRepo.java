package com.example.virtualplantstore.Repository;

import com.example.virtualplantstore.Entity.Orders;
import com.example.virtualplantstore.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders,String> {

    List<Orders> findByUser(User user);
}
