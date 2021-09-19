package com.gapfyl.dto.contents;

import com.gapfyl.dto.common.Base;
import com.gapfyl.enums.common.FileType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Getter
@Setter
public class ContentResponse extends Base {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private FileType contentType;

    private String contentUrl;

    private String thumbnailUrl;

}
