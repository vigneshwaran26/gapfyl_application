package com.gapfyl.services.products;

import com.gapfyl.dto.products.ProductResponse;
import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 29/07/21
 **/

public interface ProductService {

    void addProduct(ProductEntity productEntity, UserEntity userEntity);

    void updateProduct(ProductEntity productEntity, UserEntity userEntity);

    ProductEntity fetchProductEntity(String productId);

    ProductResponse fetchProductById(String id);

    ProductResponse fetchProductByProductId(String productId);

    List<ProductEntity> fetchProducts(List<String> productIds);
}
