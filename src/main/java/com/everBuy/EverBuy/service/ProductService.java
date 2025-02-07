package com.everBuy.EverBuy.service;

import com.everBuy.EverBuy.model.Product;
import com.everBuy.EverBuy.repository.ProductRepository;
//import com.everBuy.EverBuy.repository.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

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


//     - Number of products the merchant offers to sell
//     - Number of products sold (number of orders created)
//     - Current stock of the product
//     - Merchant rating
//     - Price of the products by various merchants
//     - Customer reviews/rating given to the products of various merchants

    public Integer totalMerchantOrder(Long merchantId) {
        String url = "http://10.65.1.185:8098/orders/merchant/"+merchantId; // Use actual API URL

        Integer totalOrder = restTemplate.getForObject(url, Integer.class, merchantId);
        return totalOrder;
    }


    public List<Long> searchSeller(Long productId) {
//        String normalizedQuery = query.toLowerCase().replaceAll("\\s+", "");
        String query = productRepository.findIdentifierBypId(productId);
        List<Long> productIds = productRepository.findByIdentifier(query);
        Map<Long, Float> merchantScore = new HashMap<>();

        float minTotal = Float.MAX_VALUE, maxTotal = Float.MIN_VALUE;

        Map<Long, Float> merchantRawScores = new HashMap<>();

        for (Long pId : productIds) {
            Float productDetails = productRepository.findSumOfProductDetails(pId);
            Long merchantId = productRepository.findMidByPid(pId);
            Integer totalOrder = totalMerchantOrder(merchantId);
            Float totalScore = productDetails + totalOrder;

            // Track min/max values
            minTotal = Math.min(minTotal, totalScore);
            maxTotal = Math.max(maxTotal, totalScore);

            merchantRawScores.put(merchantId, totalScore);
        }

        // Normalize values
        for (Map.Entry<Long, Float> entry : merchantRawScores.entrySet()) {
            Long merchantId = entry.getKey();
            Float rawScore = entry.getValue();
            Float normalizedScore = (rawScore - minTotal) / (maxTotal - minTotal);
            merchantScore.put(merchantId, normalizedScore);
        }

        return merchantScore.entrySet().stream()
                .sorted((a, b) -> Float.compare(b.getValue(), a.getValue())) // Descending order
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }


}
