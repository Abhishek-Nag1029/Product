package com.everBuy.EverBuy.service;

import com.everBuy.EverBuy.model.Product;
import com.everBuy.EverBuy.repository.ProductRepository;
//import com.everBuy.EverBuy.repository.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
//    @Autowired
//    private ProductSearchRepository productSearchRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsBymid(long mid) {
        return productRepository.findBymid(mid);
    }

    public Product getProductsBypid(long pid) {
        return productRepository.findBypid(pid);
    }

    public Long getMID(Long pid) {
        return productRepository.findMidByPid(pid);
    }

    public Product addProduct(Product product, long merchantId) {
        product.setMid(merchantId);
        return productRepository.save(product);
    }
//
    public Product updateProduct(Product product, long merchantId) {
        Product existingProduct = productRepository.findByPidAndMid(product.getPid(), merchantId)
                .orElseThrow(() -> new RuntimeException("Unauthorized: You can only update your own products"));

        return productRepository.save(product);
    }
//
    public void deleteProduct(long productId, long merchantId) {
        Product product = productRepository.findByPidAndMid(productId, merchantId)
                .orElseThrow(() -> new RuntimeException("Unauthorized: You can only delete your own products"));

        productRepository.delete(product);
    }

    public Product addRating(Integer pid, Double rating) {
        Product product = productRepository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update product's rating (this can be an average or new rating, based on your business logic)
        product.setRating(rating);
        return productRepository.save(product);
    }

    public Product decreaseStock(Long pid, Integer quantity) {
        // Find the product by product ID (pid)
        Product product = productRepository.findBypid(pid);
        System.out.println("Product found: " + product.getName());

        if (product != null) {
            if (product.getStock() >= quantity) {
                // Decrease the stock
                product.setStock(product.getStock() - quantity);
                System.out.println("Stock decreased for product: " + product.getName());
                return productRepository.save(product); // Save the updated product
            } else {
                // If the quantity exceeds available stock, return null or handle as per your requirement
                System.out.println("Stock not available for product: " + product.getName());
                return null;
            }
        }
        return null; // Return null if product is not found
    }

//    public List<Product> searchProducts(String query) {
//        String normalizedQuery = query.toLowerCase().replaceAll("\\s+", ""); // Normalize the search query
//        return productSearchRepository.findByProductIdentifier(normalizedQuery); // Elasticsearch search
//    }


}
