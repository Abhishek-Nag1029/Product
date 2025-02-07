package com.everBuy.EverBuy.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_descriptions")
public class ProductDescription {
    @Id
    private String id;
    private Long pid;
    private String description;
}

