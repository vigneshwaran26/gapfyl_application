package com.gapfyl.controller.common;

import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.services.ratings.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 03/08/21
 **/

@RestController
@RequestMapping("/api/1.0/ratings")
public class RatingsController extends AbstractController {

    @Autowired
    RatingsService ratingsService;

    @GetMapping("/fetch/{categoryType}/{categoryId}")
    ResponseEntity categoryRatings(@PathVariable("categoryType") CategoryType categoryType,
                                @PathVariable("categoryId") String categoryId) {
        return ResponseEntity.ok().body(ratingsService.fetchCategoryRatings(categoryType, categoryId));
    }

    @GetMapping("/fetch/user/{categoryType}/{categoryId}")
    ResponseEntity userCategoryRatings(@PathVariable("categoryType") CategoryType categoryType,
                                @PathVariable("categoryId") String categoryId) {
        return ResponseEntity.ok().body(ratingsService.fetchUserCategoryRatings(categoryType, categoryId, getCurrentUser()));
    }
}
