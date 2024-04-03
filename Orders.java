package com.example.virtualplantstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    private String id;
    private LocalDate date;
    private Long quantity;
    private String totalPrice;
    private String address;
    private String mobile;
    private String city;
    private String status;
    private int rating;

    @ManyToMany
    @JoinTable (
          name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<Products> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Transient
    private List<Cart> cart;
}
