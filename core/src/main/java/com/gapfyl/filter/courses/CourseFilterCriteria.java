package com.gapfyl.filter.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Getter
@Setter
public class CourseFilterCriteria {

    private String query;
    private List<String> categories;
    private List<String> subCategories;
    private List<String> languages;
    private List<String> creators;
    private List<String> tags;

    private int page = 0;
    private int pageSize = 20;
    private String sortField = "created_date";
    private String sortDir = Sort.Direction.ASC.name();

    @JsonIgnore
    public Pageable getPageableRequest() {
        return PageRequest.of(page, pageSize);
    }

    @JsonIgnore
    public Sort getSorting() {
        return Sort.by(sortDir, sortField);
    }

}
