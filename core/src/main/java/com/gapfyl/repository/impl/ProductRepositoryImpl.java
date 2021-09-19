package com.gapfyl.repository.impl;

import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.repository.custom.ICustomProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@Slf4j
@Service
public class ProductRepositoryImpl implements ICustomProductRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ProductEntity> fetchProducts(List<String> productIds) {
        Query query = new Query();
        Criteria criteria = Criteria.where("product_id").in(productIds);
        query.addCriteria(criteria);

        log.debug("fetching products with query {}", query);
        return mongoTemplate.find(query, ProductEntity.class);
    }
}
