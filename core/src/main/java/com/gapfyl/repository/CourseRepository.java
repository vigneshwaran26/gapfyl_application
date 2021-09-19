package com.gapfyl.repository;

import com.gapfyl.models.courses.CourseEntity;
import com.gapfyl.repository.custom.ICustomCourseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

public interface CourseRepository extends MongoRepository<CourseEntity, String>, ICustomCourseRepository {

    @Query("{'title': {'$regex': '?0', '$options': 'i'}}")
    List<CourseEntity> searchByTitle(String searchText);

}

