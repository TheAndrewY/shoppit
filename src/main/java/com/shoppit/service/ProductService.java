package com.shoppit.service;

import com.shoppit.dto.ProductDTO;
import com.shoppit.dto.ProductResponseDTO;
import com.shoppit.model.Product;
import com.shoppit.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductResponseDTO getProduct(int id){
        return productRepository.findById(id).isPresent() ? modelMapper.map(productRepository.findById(id).get(), ProductResponseDTO.class) : null;
    }

    public List<ProductResponseDTO> getProducts(){
        List<ProductResponseDTO> productsResponse = new ArrayList<>();
        List<Product> productList = productRepository.findAll();
        for(Product product : productList){
            productsResponse.add(modelMapper.map(product, ProductResponseDTO.class));
        }
        return productsResponse;
    }

    public ProductResponseDTO saveProduct(ProductDTO product){
        //If product already exists, throw exception
        if(product == null){
            return null;
        }
        Product savedProduct = modelMapper.map(product, Product.class);
        productRepository.save(savedProduct);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public boolean deleteProduct(int id){
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public ProductResponseDTO updateProduct(int id, ProductDTO product){
        if(product == null){
            return null;
        }
        Product updatedProduct = modelMapper.map(product, Product.class);
        if(productRepository.findById(id).isPresent()){
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
            return modelMapper.map(product, ProductResponseDTO.class);
        }
        return null;
    }
}
