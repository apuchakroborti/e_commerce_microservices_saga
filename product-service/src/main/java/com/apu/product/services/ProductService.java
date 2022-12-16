package com.apu.product.services;

import com.apu.commons.dto.product.ProductDto;
import com.apu.product.dto.request.ProductSearchCriteria;
import com.apu.product.exceptions.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ProductService {
    ProductDto addNewProduct(ProductDto productDto) throws GenericException;
    ProductDto getProductById(Long id) throws GenericException;
    Page<ProductDto> getAllProductsWithSearchCriteria(ProductSearchCriteria criteria, Pageable pageable) throws GenericException;
}
