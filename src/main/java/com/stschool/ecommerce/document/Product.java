package com.stschool.ecommerce.document;

import com.stschool.ecommerce.enums.Category;
import com.stschool.ecommerce.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private int maxRetailPrice;
    private float discountPercentage;
    private Category category;
    private String company;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
