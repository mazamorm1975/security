package com.zamora.security.service;

import com.zamora.security.exceptions.UserNotFoundException;
import com.zamora.security.user.Product;
//import jakarta.annotation.PostConstruct;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {

    List<Product> productoList = null;


    @PostConstruct
    public void consigueProductos(){

        productoList = IntStream.rangeClosed(1,100)
                .mapToObj(i -> Product.builder()
                        .idProducto(i)
                        .name("producto"+i)
                        .quantity(new Random().nextInt(10))
                        .price(new Random().nextInt(5000)).build()
                ).collect(Collectors.toList());
    }

    public List<Product> getProducts(){
        return productoList;
    }

    public Product getProduct(int id){
        return productoList.stream()
                .filter(producto -> producto.getIdProducto() == id)
                .findAny()
                .orElseThrow(() ->new UserNotFoundException("Product Not found"));
    }
}
