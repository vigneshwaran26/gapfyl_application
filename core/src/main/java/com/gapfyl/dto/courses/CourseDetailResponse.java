package com.gapfyl.dto.courses;

import com.gapfyl.dto.contents.ContentResponse;
import com.gapfyl.enums.common.Currency;
import com.gapfyl.enums.courses.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 19/07/21
 **/

@Getter
@Setter
public class CourseDetailResponse {

    private String id;

    private String title;

    private String description;

    private String language;

    private String category;

    private String subCategory;

    private List<String> tags;

    private CourseStatus status;

    private String currencyCode;

    private String currencySymbol;

    private Double amount;

    private boolean isActive;

    private String thumbnailUrl;

    private String previewUrl;
    
    private List<ContentResponse> contents;

}
