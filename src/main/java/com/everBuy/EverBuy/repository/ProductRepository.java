package com.everBuy.EverBuy.repository;

import com.everBuy.EverBuy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    List<Product> findAll();
    List<Product> findBymid(long mid);

    Product findBypid(long pid);

    Optional<Product> findByPidAndMid(long pid, long mid);

    @Query("SELECT p.mid FROM Product p WHERE p.pid = :pid") // Fetch only mid
    Long findMidByPid(@Param("pid") long pid);

    @Query("SELECT (p.stock + p.price + p.rating + " +
            "(SELECT COUNT(p2) FROM Product p2 WHERE p2.mid = p.mid)) " +
            "FROM Product p WHERE p.pid = :pid")
    Float findSumOfProductDetails(@Param("pid") Long pid);

    @Query("SELECT p.pid FROM Product p WHERE p.productIdentifier = :query")
    List<Long> findByIdentifier(@Param("query") String query);

    @Query("SELECT p.productIdentifier FROM Product p WHERE p.pid = :pid")
    String findIdentifierBypId(@Param("pid") Long pid);
}


//package com.everBuy.EverBuy.repository;
//
//import com.everBuy.EverBuy.model.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//// JPA repository for MySQL
//@Repository
//public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByMid(long mid);
//    Product findByPid(long pid);
//    Optional<Product> findByPidAndMid(long pid, long mid);
//
//    @Query("SELECT p.mid FROM Product p WHERE p.pid = :pid")  // Fetch only mid
//    Long findMidByPid(@Param("pid") long pid);
//}
//
////// Elasticsearch repository for product search
////@Repository
////public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
////    List<Product> findByProductIdentifier(String productIdentifier); // Fuzzy search on productIdentifier
////}
