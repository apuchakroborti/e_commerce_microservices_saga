package com.apu.product.specifications;

import com.apu.product.entity.Product;
import com.apu.product.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> withId(Long id){
        return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : cb.conjunction();
    }
    public static Specification<Product> withProductCode(String productCode){
        return (root, query, cb) -> !Utils.isNullOrEmpty(productCode) ?
                cb.like(root.get("productCode"), "%" + productCode + "%") :
                cb.conjunction();
    }
    public static Specification<Product> withBarCode(String barCode){
        return (root, query, cb) -> !Utils.isNullOrEmpty(barCode) ?
                cb.like(root.get("barCode"), "%" + barCode + "%") :
                cb.conjunction();
    }
    public static Specification<Product> withQrCode(String qrCode){
        return (root, query, cb) -> !Utils.isNullOrEmpty(qrCode) ?
                cb.equal(root.get("qrCode"), qrCode) :
                cb.conjunction();
    }
    public static Specification<Product> withProductPriceLessThanEqual(Double productPrice){
        return (root, query, cb) -> productPrice!=null?
                cb.lessThanOrEqualTo(root.get("productPrice"), productPrice) :
                cb.conjunction();
    }
    public static Specification<Product> withProductPriceGreaterThanEqual(Double productPrice){
        return (root, query, cb) -> productPrice!=null?
                cb.greaterThanOrEqualTo(root.get("productPrice"), productPrice) :
                cb.conjunction();
    }
    public static Specification<Product> withDiscountPriceLessThanEqual(Double discountPercentage){
        return (root, query, cb) -> discountPercentage!=null?
                cb.lessThanOrEqualTo(root.get("discountPercentage"), discountPercentage) :
                cb.conjunction();
    }
    public static Specification<Product> withDiscountPriceGreaterThanEqual(Double discountPercentage){
        return (root, query, cb) -> discountPercentage!=null?
                cb.greaterThanOrEqualTo(root.get("discountPercentage"), discountPercentage) :
                cb.conjunction();
    }
    public static Specification<Product> withStatus(Boolean status){
        return (root, query, cb) -> status!=null ?
                cb.equal(root.get("status"), status) :
                cb.conjunction();
    }
}
