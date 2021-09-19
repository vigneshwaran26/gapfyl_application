package com.gapfyl.controller.checkouts;

import com.gapfyl.services.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 30/07/21
 **/

@RestController
@RequestMapping("/api/1.0/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/fetch/{productId}")
    ResponseEntity fetchProduct(@PathVariable("productId") String productId) {
        return ResponseEntity.ok().body(productService.fetchProductByProductId(productId));
    }
}
