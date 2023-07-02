package com.zamora.security.demo;

import com.zamora.security.service.ProductService;
import com.zamora.security.user.Product;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    @Autowired
    private ProductService productService;

    @GetMapping("/demo-controller")
    public ResponseEntity<String> decirHola(){
        return ResponseEntity.ok("Hola desde otro end-point");
    }


    @GetMapping("/productos")
    public List<Product> getAllProducts(){
        return productService.getProducts();
    }

}
