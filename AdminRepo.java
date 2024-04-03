package com.example.virtualplantstore.Repository;


import com.example.virtualplantstore.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,String> {
}
