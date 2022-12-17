package com.apu.product.services.impls;

import com.apu.commons.dto.product.ProductDto;
import com.apu.product.dto.request.ProductSearchCriteria;
import com.apu.product.entity.Product;
import com.apu.product.exceptions.GenericException;
import com.apu.product.repository.ProductRepository;
import com.apu.product.services.ProductService;
import com.apu.product.specifications.ProductSpecifications;
import com.apu.product.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDto addNewProduct(ProductDto productDto) throws GenericException {
        try{
            Product product = new Product();
            Utils.copyProperty(productDto, product);
            //TODO need to save the image urls into the MinIO

            product.setStatus(true);
            product.setCreatedBy(1L);
            product.setCreateTime(LocalDateTime.now());

            product = productRepository.save(product);

            Utils.copyProperty(product, productDto);
            return productDto;
        }catch (Exception e){
            throw new GenericException("Exception occurred while adding new Product!");
        }

    }

    @Override
    public ProductDto getProductById(Long id) throws GenericException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(!productOptional.isPresent())throw new GenericException("Product not found by id: "+id);

        ProductDto productDto = new ProductDto();
        Utils.copyProperty(productOptional.get(), productDto);
        return productDto;
    }

    @Override
    public Page<ProductDto> getAllProductsWithSearchCriteria(ProductSearchCriteria criteria, Pageable pageable) throws GenericException{
        Page<Product> productPage = productRepository.findAll(
                ProductSpecifications.withId(criteria.getProductId())
                .and(ProductSpecifications.withProductCode(criteria.getProductCode()))
                .and(ProductSpecifications.withBarCode(criteria.getBarCode()))
                .and(ProductSpecifications.withQrCode(criteria.getQrCode()))
                .and(ProductSpecifications.withProductPriceGreaterThanEqual(criteria.getProductPriceRangeLow()))
                .and(ProductSpecifications.withProductPriceLessThanEqual(criteria.getProductPriceRangeHigh()))
                .and(ProductSpecifications.withDiscountPriceGreaterThanEqual(criteria.getDiscountPercentageRangeLow()))
                .and(ProductSpecifications.withDiscountPriceLessThanEqual(criteria.getDiscountPercentageRangeHigh()))
                .and(ProductSpecifications.withStatus(true))
                ,
                pageable);

        Page<ProductDto> productDtoPage = productPage.map(new Function<Product, ProductDto>() {
            @Override
            public ProductDto apply(Product entity) {
                ProductDto dto = new ProductDto();
                Utils.copyProperty(entity, dto);
                return dto;
            }
        });

        return productDtoPage;
    }

}
