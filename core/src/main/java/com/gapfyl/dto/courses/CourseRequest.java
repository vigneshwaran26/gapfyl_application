package com.gapfyl.dto.courses;

import com.gapfyl.dto.common.Base;
import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.dto.contents.ContentResponse;
import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author vignesh
 * Created on 02/05/21
 **/

@Getter
@Setter
public class CourseRequest extends Base {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String subCategory;

    @NotEmpty
    private List<String> tags;

    @NotBlank
    private String thumbnailUrl;

    @NotBlank
    private String previewUrl;

    @NotBlank
    private String language;

    @NotNull
    private Double amount;

    @NotNull
    private Currency currency;

    @NotEmpty
    List<ContentRequest> contents;
}
