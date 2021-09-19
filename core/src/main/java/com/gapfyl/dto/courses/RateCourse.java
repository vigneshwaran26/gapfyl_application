package com.gapfyl.dto.courses;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author vignesh
 * Created on 30/07/21
 **/

@Getter
@Setter
public class RateCourse {

    @NotBlank
    public String courseId;

    @NotNull
    public int rating;
}
