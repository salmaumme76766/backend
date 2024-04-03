package com.example.virtualplantstore.Repository;

import com.example.virtualplantstore.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository <Products,Integer> {


}
