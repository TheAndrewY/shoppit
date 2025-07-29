package com.shoppit.controller;

import com.shoppit.dto.ProductDTO;
import com.shoppit.dto.ProductResponseDTO;
import com.shoppit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> products = productService.getProducts();
        if(products == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable int id) {
        ProductResponseDTO response = productService.getProduct(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductDTO product) {
        ProductResponseDTO savedProduct = productService.saveProduct(product);
        if(savedProduct != null){
            return ResponseEntity.ok(savedProduct);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        if(productService.deleteProduct(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable int id, @RequestBody ProductDTO product) {
        ProductResponseDTO responseDTO = productService.updateProduct(id,product);
        if(responseDTO == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(responseDTO);
    }
}
