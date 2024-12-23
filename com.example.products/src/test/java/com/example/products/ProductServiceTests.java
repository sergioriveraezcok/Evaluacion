package com.example.products;

import com.example.products.entity.Product;
import com.example.products.repository.ProductRepository;
import com.example.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

// ===========================
// Tests: ProductServiceTests
// ===========================
@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        List<Product> products = List.of(Product.builder()
                .id(1L)
                .name("Test Product")
                .price(BigDecimal.valueOf(99.99))
                .build());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
    }

    @Test
    public void testCreateProduct() {
        Product product = Product.builder()
                .name("New Product")
                .price(BigDecimal.valueOf(50.00))
                .build();
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertEquals("New Product", createdProduct.getName());
        assertEquals(BigDecimal.valueOf(50.00), createdProduct.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        Product existingProduct = Product.builder()
                .id(1L)
                .name("Old Product")
                .price(BigDecimal.valueOf(40.00))
                .build();
        Product updatedProductDetails = Product.builder()
                .name("Updated Product")
                .price(BigDecimal.valueOf(60.00))
                .build();

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProduct(1L, updatedProductDetails);

        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(BigDecimal.valueOf(60.00), updatedProduct.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("Product to Delete")
                .build();
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        productService.deleteProduct(1L);

        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
}


