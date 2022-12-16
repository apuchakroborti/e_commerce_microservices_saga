package com.apu.product.controllers;

import com.apu.commons.dto.APIResponse;
import com.apu.commons.dto.Pagination;
import com.apu.commons.dto.product.ProductDto;
import com.apu.product.dto.request.ProductSearchCriteria;
import com.apu.product.exceptions.GenericException;
import com.apu.product.utils.Utils;
import com.apu.product.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<APIResponse> addNewProduct(@Valid @RequestBody ProductDto productDto) throws GenericException {
        log.info("ProductController::addNewProduct request: {}", Utils.jsonAsString(productDto));

        ProductDto result = productService.addNewProduct(productDto);

        APIResponse<ProductDto> responseDTO = APIResponse
                .<ProductDto>builder()
                .status("SUCCESS")
                .results(result)
                .build();

        log.info("ProductController::addNewProduct response: {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllProductsBySearchCriteria(ProductSearchCriteria criteria, @PageableDefault(value = 12) Pageable pageable) throws GenericException {
        log.info("ProductController::getAllProductsBySearchCriteria request: {}", Utils.jsonAsString(criteria));
        Page<ProductDto> productDtoPage = productService.getAllProductsWithSearchCriteria(criteria, pageable);

        APIResponse<List<ProductDto>> responseDTO = APIResponse
                .<List<ProductDto>>builder()
                .status("SUCCESS")
                .results(productDtoPage.getContent())
                .pagination(new Pagination(productDtoPage.getTotalElements(),
                        productDtoPage.getNumberOfElements(),
                        productDtoPage.getNumber(),
                        productDtoPage.getSize()))
                .build();

        log.info("ProductController::getAllProductsBySearchCriteria response: {}", Utils.jsonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
