package com.gapfyl.controller.common;

import com.gapfyl.services.courses.CourseService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@RestController
@RequestMapping("/api/1.0/data")
public class DataProcessController extends AbstractController {

    @Autowired
    CourseService courseService;

    @PutMapping("/update-course-products")
    ResponseEntity saveOrUpdateCoursesAsProduct() {
        courseService.saveOrUpdateCoursesAsProduct(getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

}
