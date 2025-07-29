package com.shoppit.controller;


import com.shoppit.dto.ProductResponseDTO;
import com.shoppit.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@WithMockUser(username = "admin", roles = "ADMIN")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void testSuccessfulGetProducts() throws Exception {
        ProductResponseDTO productResponse1 = createProductResponseDTO(1, "Product1", "test", 1, 1);
        ProductResponseDTO productResponse2 = createProductResponseDTO(1, "Product2", "testy", 12, 123);
        when(productService.getProducts()).thenReturn(Arrays.asList(productResponse1, productResponse2));
        mockMvc.perform(get("/api/products"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json")
                );
    }
    @Test
    void testFailureGetProducts() throws Exception {
        when(productService.getProducts()).thenReturn(null);
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isNotFound());
    }

    private ProductResponseDTO createProductResponseDTO(int id,String name, String description, double price, int quantity) {
        ProductResponseDTO product = new ProductResponseDTO();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }

}
