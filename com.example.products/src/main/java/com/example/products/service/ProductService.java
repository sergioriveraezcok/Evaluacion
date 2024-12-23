package com.example.products.service;

import com.example.products.entity.Product;
import com.example.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// ===========================
// Service: ProductService
// ===========================
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Obtiene todos los productos de la base de datos.
     * @return Lista de productos.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Busca un producto por su ID.
     * @param id ID del producto.
     * @return Producto encontrado.
     * @throws ResponseStatusException Si el producto no existe.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    /**
     * Crea un nuevo producto en la base de datos.
     * @param product Producto a crear.
     * @return Producto creado.
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Actualiza un producto existente.
     * @param id ID del producto a actualizar.
     * @param productDetails Detalles del producto actualizados.
     * @return Producto actualizado.
     * @throws ResponseStatusException Si el producto no existe.
     */
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        return productRepository.save(existingProduct);
    }

    /**
     * Elimina un producto de la base de datos.
     * @param id ID del producto a eliminar.
     * @throws ResponseStatusException Si el producto no existe.
     */
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
