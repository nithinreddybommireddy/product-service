package com.stschool.ecommerce.service;

import com.stschool.ecommerce.document.Product;
import com.stschool.ecommerce.dto.request.ProductRequestDto;
import com.stschool.ecommerce.dto.response.ProductResponseDto;
import com.stschool.ecommerce.enums.Category;
import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    ProductResponseDto save(ProductRequestDto requestDto) throws ProductExistsException;
    ProductResponseDto getById(String id) throws ProductNotFoundException;
    List<ProductResponseDto> getAll();
    ProductResponseDto update(String id, ProductRequestDto requestDto) throws ProductNotFoundException;
    void delete(String id) throws ProductNotFoundException;
    List<ProductResponseDto> saveAll(List<ProductRequestDto> requestDto);

    List<Product> getProductsByAvailability(boolean isAvailable);


    List<ProductResponseDto> getAllProductsAbovePrice(int price);

    List<String> getAllProductNames();

    long getAvailableProductsCount();

    boolean existsByCompany(String company);


    Optional<Product> getFirstProduct();

    List<Category> getAllUniqueCategories();

    List<Product> getTopNExpensiveProducts(int n);

    List<ProductResponseDto> sortProductsByPriceAsc();

    List<ProductResponseDto> sortProductsByPriceDesc();

    Long getTotalInventoryValue();

    double getTotalPriceAfterDiscount();

//    List<Product> getProductsByManufacturedAfter(int year);

    List<Product> getProductsByAvailableAndPriceAbove(boolean isAvailable, double price);

    Map<Category, List<Product>> getProductsByCategory();

    Map<String, List<Product>> getProductsByCompany();

    Map<Boolean, List<Product>> partitionProductsByAvailability();

    Optional<Product> getMostExpensiveProduct();

    Optional<ProductResponseDto> getCheapestProduct();

    Map<String, Product> mapProductsById();

    Map<Category, Double> getAveragePriceByCategory();

    Map<Category, List<Product>> getTop3ExpensiveProductsByCategory();

}

