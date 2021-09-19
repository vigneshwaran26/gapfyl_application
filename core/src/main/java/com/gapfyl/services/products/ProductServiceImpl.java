package com.gapfyl.services.products;

import com.gapfyl.dto.products.ProductResponse;
import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.ProductRepository;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vignesh
 * Created on 29/07/21
 **/

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductResponse entityToResponse(ProductEntity productEntity) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(productEntity.getProductId());
        productResponse.setName(productEntity.getName());
        productResponse.setDescription(productEntity.getDescription());
        productResponse.setCategory(productEntity.getCategory());
        productResponse.setSubCategory(productEntity.getSubCategory());
        productResponse.setType(productEntity.getType());

        if (productEntity.getPricing() != null) {
            productResponse.setAmount(productEntity.getPricing().getAmount());
            productResponse.setCurrency(productEntity.getPricing().getCurrency());
        }

        productResponse.setActive(productEntity.isActive());
        productResponse.setFree(productEntity.isFree());
        return  productResponse;
    }

    @Override
    public void addProduct(ProductEntity productEntity, UserEntity userEntity) {
        log.debug("user {} [{}] adding product {}", userEntity.getName(), userEntity.getId(), productEntity);
        productEntity.setCreatedBy(userEntity);
        productEntity.setModifiedBy(userEntity);
        productEntity.setCreatedDate(Common.getCurrentUTCDate());
        productEntity.setModifiedDate(Common.getCurrentUTCDate());
        productEntity = productRepository.save(productEntity);
        log.info("user {} [{}] adding product {}", userEntity.getName(), userEntity.getId(), productEntity);
    }

    @Override
    public void updateProduct(ProductEntity productEntity, UserEntity userEntity) {
        log.debug("user {} [{}] adding product {}", userEntity.getName(), userEntity.getId(), productEntity);
        productEntity.setCreatedBy(userEntity);
        productEntity.setModifiedBy(userEntity);
        productEntity.setCreatedDate(Common.getCurrentUTCDate());
        productEntity.setModifiedDate(Common.getCurrentUTCDate());
        productEntity = productRepository.save(productEntity);
        log.info("user {} [{}] adding product {}", userEntity.getName(), userEntity.getId(), productEntity);
    }

    @Override
    public ProductResponse fetchProductById(String id) {
         ProductEntity productEntity = productRepository.findById(id).orElse(null);
         if (productEntity == null) {
             log.error("product not found {}", id);
             return null;
         }

         return entityToResponse(productEntity);
    }

    @Override
    public ProductEntity fetchProductEntity(String productId) {
        return  productRepository.findOneByProductId(productId);
    }

    @Override
    public ProductResponse fetchProductByProductId(String productId) {
        ProductEntity productEntity = fetchProductEntity(productId);
        if (productEntity == null) {
            log.error("product not found {}", productId);
            return null;
        }

        return entityToResponse(productEntity);
    }

    @Override
    public List<ProductEntity> fetchProducts(List<String> productIds) {
        return productRepository.fetchProducts(productIds);
    }
}
