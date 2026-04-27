package com.example.ordersmicroservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_line")
@Data
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private Long Product_Id;

    private Long Order_Id;

    private Integer Quantity;

    private Float Unit_Price;
}
