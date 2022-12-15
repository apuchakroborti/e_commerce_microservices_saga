package com.apu.product.services;

import com.apu.product.dto.request.ProductSearchCriteria;
import com.apu.product.exceptions.GenericException;
import com.apu.product.dto.ProductDto;
import com.apu.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;


public interface ProductService {
    ProductDto addNewProduct(ProductDto productDto) throws GenericException;
    Page<ProductDto> getAllProductsWithSearchCriteria(ProductSearchCriteria criteria, Pageable pageable) throws GenericException;
}
