package com.zamora.security.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tb_product")
public class Product {

    @Id
    @GeneratedValue
    private Integer idProducto;

    @Column(name="name")
    private String name;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="price")
    private float price;
}
