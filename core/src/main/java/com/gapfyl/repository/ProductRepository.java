package com.gapfyl.repository;

import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.repository.custom.ICustomProductRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 29/07/21
 **/

public interface ProductRepository extends MongoRepository<ProductEntity, String>, ICustomProductRepository {

    ProductEntity findOneByProductId(String productId);
}
