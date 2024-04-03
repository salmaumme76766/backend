package com.example.virtualplantstore.Controller;

import com.example.virtualplantstore.DTO.LoginDTO;
import com.example.virtualplantstore.Repository.AdminRepo;
import com.example.virtualplantstore.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    public AdminRepo adminRepo;

    @Autowired
    public UserRepo userRepo;

    @PostMapping("/LoginVerify")
    public ResponseEntity<?> loginVerify(@RequestBody LoginDTO obj) {
        if (obj.getUserType().equals("admin")) {
            var admin = adminRepo.findById(obj.getEmail()).orElseThrow(() -> new RuntimeException("Admin not found"));
            if (admin.getPassword().equals(obj.getPassword())) {
                return new ResponseEntity<>("admin", HttpStatus.OK);
            } else {
                throw new RuntimeException("invalid password");
            }
        }
        else if (obj.getUserType().equals("user")) {
            var user = userRepo.findByEmail(obj.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
            if (user.getPassword().equals(obj.getPassword())) {
                return new ResponseEntity<>("user", HttpStatus.OK);
            } else {
                throw new RuntimeException("Invalid password");
            }
        }
        else {
            throw new RuntimeException("Invalid user type");
        }
    }
}
