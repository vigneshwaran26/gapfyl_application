package com.gapfyl.dto.discussions;

import com.gapfyl.enums.common.CategoryType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author vignesh
 * Created on 05/05/21
 **/

@Getter
@Setter
public class DiscussionRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private CategoryType category;

    @NotBlank
    private String categoryId;

    private List<String> tags;
}
