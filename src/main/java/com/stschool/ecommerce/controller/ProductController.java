package com.stschool.ecommerce.controller;


import com.stschool.ecommerce.document.Product;
import com.stschool.ecommerce.dto.request.ProductRequestDto;
import com.stschool.ecommerce.dto.response.ApiResponseDto;
import com.stschool.ecommerce.dto.response.ProductResponseDto;
import com.stschool.ecommerce.enums.Category;
import com.stschool.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> save(@RequestBody ProductRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product created successfully")
                        .code(HttpStatus.CREATED.value())
                        .data(productService.save(requestDto))
                        .build()
        );
    }

    @PostMapping("/all")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> saveAll(
            @RequestBody List<ProductRequestDto> products) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponseDto.<List<ProductResponseDto>>builder()
                                .success(true)
                                .message("Products created successfully")
                                .code(HttpStatus.CREATED.value())
                                .data(productService.saveAll(products))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAll() {
        return ResponseEntity.ok(
                ApiResponseDto.<List<ProductResponseDto>>builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.getAll())
                        .build()
        );

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getBydId(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product fetched successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.getById(id))
                        .build()
        );
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> update(@PathVariable String id, @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(
                ApiResponseDto.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product updated successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.update(id, productRequestDto))
                        .build()
        );


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponseDto.<Void>builder()
                        .success(true)
                        .message("Product deleted successfully")
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/availability/{isAvailable}")
    public ResponseEntity<ApiResponseDto<List<Product>>> getProductsByAvailability(
            @PathVariable boolean isAvailable) {

        return ResponseEntity.ok(
                ApiResponseDto.<List<Product>>builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.getProductsByAvailability(isAvailable))
                        .build()
        );
    }

    @GetMapping("/company")
    public ResponseEntity<ApiResponseDto<Map<String, List<Product>>>> getProductsByCompany() {

        return ResponseEntity.ok(
                ApiResponseDto.<Map<String, List<Product>>>builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .code(HttpStatus.OK.value())
                        .data(productService.getProductsByCompany())
                        .build()
        );
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponseDto<Map<Category, List<Product>>>> getProductsByCategory() {
        return ResponseEntity.ok(ApiResponseDto.<Map<Category, List<Product>>>builder()
                .success(true)
                .message("Products fetched successfully")
                .code(HttpStatus.OK.value())
                .data(productService.getProductsByCategory())
                .build());
    }

    @GetMapping("/unique/category")
    public ResponseEntity<ApiResponseDto<List<Category>>> getAllUniqueCategories() {
        return ResponseEntity.ok(ApiResponseDto.<List<Category>>builder()
                .success(true).message("Products fetched successfully")
                .code(HttpStatus.OK.value())
                .data(productService.getAllUniqueCategories())
                .build());
    }

    @GetMapping("/available/count")
    public ResponseEntity<ApiResponseDto<Long>> getProductsAvailableCount() {
        return ResponseEntity.ok(ApiResponseDto.<Long>builder().success(true).message("Products fetched successfully").code(HttpStatus.OK.value()).data(productService.getAvailableProductsCount()).build());
    }

    @GetMapping("/cheapest")
    public ResponseEntity<ApiResponseDto<Optional<ProductResponseDto>>> getCheapestProduct() {
        return ResponseEntity.ok(ApiResponseDto.<Optional<ProductResponseDto>>builder().success(true).message("Products fetched successfully").code(HttpStatus.OK.value()).data(productService.getCheapestProduct()).build());
    }

    @GetMapping("/Asc")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> sortProductsByPriceAsc() {
        return ResponseEntity.ok(ApiResponseDto.<List<ProductResponseDto>>builder()
                .success(true).message("Products Sorted successfully").code(HttpStatus.OK.value()).data(productService.sortProductsByPriceAsc()).build());
    }

    @GetMapping("/Dsc")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> sortProductsByPriceDesc() {
        return ResponseEntity.ok(ApiResponseDto.<List<ProductResponseDto>>builder()
                .success(true)
                .message("Products Sorted Successfully")
                .code(HttpStatus.OK.value())
                .data(productService.sortProductsByPriceDesc())
                .build());
    }

    @GetMapping("/above-price/{price}")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProductsAbovePrice(@PathVariable int price) {
        return ResponseEntity.ok(ApiResponseDto.<List<ProductResponseDto>>builder()
                .success(true)
                .message("Products above price:" + price)
                .code(HttpStatus.OK.value())
                .data(productService.getAllProductsAbovePrice(price))
                .build());
    }
}


