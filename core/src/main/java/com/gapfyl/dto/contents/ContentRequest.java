package com.gapfyl.dto.contents;

import com.gapfyl.dto.common.Base;
import com.gapfyl.enums.common.FileType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author vignesh
 * Created on 05/05/21
 **/

@Getter
@Setter
public class ContentRequest extends Base {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String contentUrl;

    @NotNull
    private FileType contentType;
}
