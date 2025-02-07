package com.everBuy.EverBuy.controller;

import com.everBuy.EverBuy.model.Product;
import com.everBuy.EverBuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct/{mid}")
    public Product postProduct(@RequestBody Product product, @PathVariable Long mid) {
        return productService.addProduct(product, mid);
    }

    @GetMapping("/getmidFrompid/{pid}")
    public Long getMidfromPid(@PathVariable Long pid) {
        return productService.getMID(pid);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }
//
    @GetMapping("/products/{mid}")
    public List<Product> getProductsByMerchant(@PathVariable Long mid) {
        return productService.getProductsBymid(mid);
    }

    @GetMapping("/products/product/{pid}")
    public Product getProductsByPid(@PathVariable Long pid) {
        return productService.getProductsBypid(pid);
    }
//
    @PutMapping("/products/{mid}")
    public void updateProduct(@RequestBody Product product, @PathVariable Long mid) {
        productService.updateProduct(product, mid);
    }
//
    @DeleteMapping("/products/{productId}/merchant/{mid}")
    public void deleteProduct(@PathVariable Long productId, @PathVariable Long mid) {
        productService.deleteProduct(productId, mid);
    }

    @PostMapping("/rating")
    public ResponseEntity<Product> addRating(
            @RequestParam Integer pid,
            @RequestParam Double rating) {
        Product updatedProduct = productService.addRating(pid, rating);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/products/decStock/{pid}/{quantity}")
    public ResponseEntity<Product> decreaseStock(
            @PathVariable Long pid,
            @PathVariable Integer quantity) {

        // Call service method to decrease the stock
        System.out.println(pid);
        System.out.println(quantity);
        Product updatedProduct = productService.decreaseStock(pid, quantity);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().body(null);  // Return bad request if product is not found or quantity exceeds stock
        }
    }

    @GetMapping("/products/search/{pId}")
    public List<Long> searchProducts(@PathVariable Long pId) {
        return productService.searchSeller(pId);
    }



//    add ratings api
    //get total number products of mid
}
