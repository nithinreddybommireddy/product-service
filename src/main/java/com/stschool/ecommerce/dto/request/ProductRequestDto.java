package com.stschool.ecommerce.dto.request;

import com.stschool.ecommerce.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private int maxRetailPrice;
    private float discountPercentage;
    private Category category;
    private String company;
}
