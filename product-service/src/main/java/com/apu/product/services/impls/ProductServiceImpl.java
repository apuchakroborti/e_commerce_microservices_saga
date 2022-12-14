package com.apu.product.services.impls;

import com.apu.product.dto.request.ProductSearchCriteria;
import com.apu.product.entity.Product;
import com.apu.product.exceptions.GenericException;
import com.apu.product.repository.ProductRepository;
import com.apu.product.services.ProductService;
import com.apu.product.utils.Utils;
import com.apu.product.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public Page<Product> getAllProductsWithSearchCriteria(ProductSearchCriteria criteria, Pageable pageable) throws GenericException{
        Page<Product> productPage = productRepository.findAll(null,
                pageable);

        return productPage;
    }

}
