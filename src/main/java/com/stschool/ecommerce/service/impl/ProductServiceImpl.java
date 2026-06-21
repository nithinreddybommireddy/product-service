package com.stschool.ecommerce.service.impl;

import com.stschool.ecommerce.document.Product;
import com.stschool.ecommerce.dto.request.ProductRequestDto;
import com.stschool.ecommerce.dto.response.ProductResponseDto;
import com.stschool.ecommerce.enums.Category;
import com.stschool.ecommerce.enums.Status;
import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;
import com.stschool.ecommerce.repository.ProductRepository;
import com.stschool.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto save(ProductRequestDto requestDto) throws ProductExistsException {
        productRepository.findByName(requestDto.getName()).ifPresent(product -> {
            throw new ProductExistsException("Product already exists with the name: " + requestDto.getName());
        });
        Product product = modelMapper.map(requestDto, Product.class);
        product.setStatus(Status.AVAILABLE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return modelMapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getById(String id) throws ProductNotFoundException {

        return modelMapper.map(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id)), ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto requestDto) throws ProductNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
        return modelMapper.map(productRepository.save(modelMapper.map(requestDto, Product.class)), ProductResponseDto.class);

    }

    @Override
    public void delete(String id) throws ProductNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> saveAll(List<ProductRequestDto> requestDtos) {

        return requestDtos.stream()
                .map(dto -> {
                    Product product = modelMapper.map(dto, Product.class);
                    product.setStatus(Status.AVAILABLE);
                    product.setCreatedAt(LocalDateTime.now());
                    product.setUpdatedAt(LocalDateTime.now());

                    return modelMapper.map(
                            productRepository.save(product),
                            ProductResponseDto.class
                    );
                })
                .toList();
    }

    @Override
    public List<Product> getProductsByAvailability(boolean isAvailable) {
        return productRepository.findAll().stream().filter(product -> product.getStatus() == Status.AVAILABLE).toList();

    }


    @Override
    public List<ProductResponseDto> getAllProductsAbovePrice(int price) {
        return productRepository.findAll().stream().filter(product -> product.getMaxRetailPrice() > price).map(product -> modelMapper.map(product,ProductResponseDto.class)).toList();
    }

    @Override
    public List<String> getAllProductNames() {
        return productRepository.findAll().stream().map(Product::getName).distinct().toList();

    }

    @Override
    public long getAvailableProductsCount() {
        return productRepository.findAll().stream().filter(product -> product.getStatus() == Status.AVAILABLE).count();
    }

    @Override
    public boolean existsByCompany(String company) {
        return productRepository.findAll().stream().anyMatch(product -> product.getCompany().equalsIgnoreCase(company));
    }

    @Override
    public Optional<Product> getFirstProduct() {
        return productRepository.findAll().stream().findFirst();
    }

    @Override
    public List<Category> getAllUniqueCategories() {
        return productRepository.findAll().stream().map(Product::getCategory).distinct().toList();
    }

    @Override
    public List<Product> getTopNExpensiveProducts(int n) {
        return productRepository.findAll().stream().sorted(Comparator.comparing(Product::getMaxRetailPrice).reversed()).limit(n).toList();
    }

    @Override
    public List<ProductResponseDto> sortProductsByPriceAsc() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getMaxRetailPrice))
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> sortProductsByPriceDesc() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getMaxRetailPrice, Comparator.reverseOrder()))
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();

    }

    @Override
    public Long getTotalInventoryValue() {
        return productRepository.findAll().stream().mapToLong(Product::getMaxRetailPrice).sum();
    }

    @Override
    public double getTotalPriceAfterDiscount() {
        return productRepository.findAll().stream().mapToDouble(product -> product.getMaxRetailPrice() - (product.getMaxRetailPrice() * product.getDiscountPercentage() / 100)).sum();
    }
//
//    @Override
//    public List<Product> getProductsByManufacturedAfter(int year) {
//        return productRepository.findAll().stream().filter(product -> product.getManufactureYear() >= year).toList();
//    }

    @Override
    public List<Product> getProductsByAvailableAndPriceAbove(boolean isAvailable, double price) {
        return productRepository.findAll().stream().filter(product -> product.getStatus() == Status.AVAILABLE).filter(product -> product.getMaxRetailPrice() > price).toList();
    }

    @Override
    public Map<Category, List<Product>> getProductsByCategory() {
        return productRepository.findAll().stream().collect(Collectors.groupingBy(Product::getCategory));
    }

    @Override
    public Map<String, List<Product>> getProductsByCompany() {
        return productRepository.findAll().stream().collect(Collectors.groupingBy(Product::getCompany));
    }

    @Override
    public Map<Boolean, List<Product>> partitionProductsByAvailability() {
        return productRepository.findAll().stream().collect(Collectors.partitioningBy(product -> product.getStatus() == Status.AVAILABLE));
    }

    @Override
    public Optional<Product> getMostExpensiveProduct() {
        return productRepository.findAll().stream().max(Comparator.comparingDouble(Product::getMaxRetailPrice));
    }

    @Override
    public Optional<ProductResponseDto> getCheapestProduct() {
        return productRepository.findAll()
                .stream()
                .min(Comparator.comparingDouble(Product::getMaxRetailPrice))
                .map(product -> modelMapper.map(product, ProductResponseDto.class));
    }

    @Override
    public Map<String, Product> mapProductsById() {
        return productRepository.findAll().stream().collect(Collectors.toMap(Product::getId, product -> product));
    }

    @Override
    public Map<Category, Double> getAveragePriceByCategory() {
        return productRepository.findAll().stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.averagingDouble(Product::getMaxRetailPrice)));
    }

    @Override
    public Map<Category, List<Product>> getTop3ExpensiveProductsByCategory() {
        return productRepository.findAll().stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.collectingAndThen(Collectors.toList(), products -> products.stream().sorted(Comparator.comparing(Product::getMaxRetailPrice).reversed()).limit(3).toList())));

    }

}

