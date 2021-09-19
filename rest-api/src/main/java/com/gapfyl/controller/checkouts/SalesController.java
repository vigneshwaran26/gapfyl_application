package com.gapfyl.controller.checkouts;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.checkouts.sales.SalesRequest;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.services.checkouts.sales.SalesService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@RestController
@RequestMapping("/api/1.0/sales")
public class SalesController extends AbstractController {

    @Autowired
    SalesService salesService;

    @PostMapping("/create")
    ResponseEntity addSales(@RequestBody SalesRequest salesRequest) throws NotFoundException {
        salesService.addSales(salesRequest, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

}
