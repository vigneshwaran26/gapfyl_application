package com.gapfyl.dto.courses;

import com.gapfyl.dto.common.Base;
import com.gapfyl.dto.contents.ContentResponse;
import com.gapfyl.enums.courses.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 20/04/21
 **/

@Getter
@Setter
public class Course extends Base {

    private String title;

    private String description;

    private String language;

    private CourseStatus courseStatus;

    private List<ContentResponse> contentResponses;
}
