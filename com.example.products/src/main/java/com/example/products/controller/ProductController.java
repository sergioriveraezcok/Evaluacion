package com.example.products.controller;

import com.example.products.entity.Product;
import com.example.products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ===========================
// Controller: ProductController
// ===========================
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor con inyección de dependencia
    // Usamos un constructor explícito con @Autowired eliminada porque Spring Boot la infiere automáticamente.
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Obtiene todos los productos.
     * @return Lista de productos.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Obtiene un producto por su ID.
     * @param id ID del producto.
     * @return Detalles del producto como ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Crea un nuevo producto.
     * @param product Detalles del producto enviados en el cuerpo de la solicitud.
     * @return Producto creado con estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar.
     * @param productDetails Detalles actualizados del producto.
     * @return Producto actualizado como ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar.
     * @return Respuesta vacía con estado HTTP 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}