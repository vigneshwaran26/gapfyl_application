package com.gapfyl.repository.custom;

import com.gapfyl.models.products.ProductEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

public interface ICustomProductRepository {

    List<ProductEntity> fetchProducts(List<String> productIds);
}
