package com.example.virtualplantstore.Controller;

import com.example.virtualplantstore.Entity.User;
import com.example.virtualplantstore.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    public UserRepo userRepo;

    @PostMapping("/Registration")
    public ResponseEntity<?> registration(@RequestBody User users) {
        if (userRepo.existsByEmail(users.getEmail())) {
            throw new RuntimeException("Email id already exist");
        }
        return new ResponseEntity<>(userRepo.save(users), HttpStatus.OK);
    }

    @GetMapping("/GetAllUsers")
    public ResponseEntity<?> getAllUsers() {
        var userdata = userRepo.findAll();
        return new ResponseEntity<>(userdata, HttpStatus.OK);
    }
    @GetMapping("/GetUserByEmail/{email}")
    public ResponseEntity<?> getAllUser(@PathVariable String email) {
        var user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User already exist"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("UpdateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User obj) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(obj.getStatus());
        userRepo.save(user);
        return new ResponseEntity<>("User updated sucessfully", HttpStatus.OK);
    }

    @PutMapping("Updateprofile/{id}")
    public ResponseEntity<?> updateprofile(@PathVariable Integer id, @RequestBody User user) {
        User dbuser = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        dbuser.setName(user.getName());
        dbuser.setEmail(user.getEmail());
        dbuser.setMobile(user.getMobile());
        dbuser.setCity(user.getCity());
        dbuser.setAddress(user.getAddress());
        dbuser.setPassword(user.getPassword());
        return new ResponseEntity<>(userRepo.save(dbuser), HttpStatus.OK);

    }
}