//package com.everBuy.EverBuy.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.AllArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "products")
//@NoArgsConstructor
//@AllArgsConstructor
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long pid;
//
//    private String name;
//    private String category;
//    private BigDecimal price;
//    private Integer stock;
//    private Double rating;
//
//    private Long mid; // Merchant ID
//
//    private String brand;
//    private String productIdentifier;
//    private String descriptionId; // MongoDB reference
//
//    @ElementCollection
//    private List<String> imageUrls; // Firebase/S3 URLs
//
//
//    public void setProductIdentifier(String productIdentifier) {
//        this.productIdentifier = productIdentifier.toLowerCase().replaceAll("[^a-z0-9]", "");
//    }
//
//    // Getters
////    public Long getPid() {
////        return pid;
////    }
////
////    public String getName() {
////        return name;
////    }
////
////    public String getCategory() {
////        return category;
////    }
////
////    public BigDecimal getPrice() {
////        return price;
////    }
////
////    public Integer getStock() {
////        return stock;
////    }
////
////    public Double getRating() {
////        return rating;
////    }
////
////    public Long getMid() {
////        return mid;
////    }
////
////    public String getBrand() {
////        return brand;
////    }
////
////    public String getProductIdentifier() {
////        return productIdentifier;
////    }
////
////    public String getDescriptionId() {
////        return descriptionId;
////    }
////
////    public List<String> getImageUrls() {
////        return imageUrls;
////    }
////
////    // Setters
////    public void setPid(Long pid) {
////        this.pid = pid;
////    }
////
////    public void setName(String name) {
////        this.name = name;
////    }
////
////    public void setCategory(String category) {
////        this.category = category;
////    }
////
////    public void setPrice(BigDecimal price) {
////        this.price = price;
////    }
////
////    public void setStock(Integer stock) {
////        this.stock = stock;
////    }
////
////    public void setRating(Double rating) {
////        this.rating = rating;
////    }
////
////    public void setMid(Long mid) {
////        this.mid = mid;
////    }
////
////    public void setBrand(String brand) {
////        this.brand = brand;
////    }
////
//
////
////    public void setDescriptionId(String descriptionId) {
////        this.descriptionId = descriptionId;
////    }
////
////    public void setImageUrls(List<String> imageUrls) {
////        this.imageUrls = imageUrls;
////    }
//}


package com.everBuy.EverBuy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
//import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "products")  // Elasticsearch indexing
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String name;
    private String category;
    private BigDecimal price;
    private Integer stock;
    private Double rating;
    private Long mid; // Merchant ID
    private String brand;
    private String productIdentifier; // Used for search
    private String descriptionId; // MongoDB reference

    @ElementCollection
    private List<String> imageUrls; // Firebase/S3 URLs

    @PrePersist
    @PreUpdate
    public void generateProductIdentifier() {
        if (this.name != null) {
            this.productIdentifier = name.toLowerCase().replaceAll("[^a-z0-9]", "");
        }
    }
}
