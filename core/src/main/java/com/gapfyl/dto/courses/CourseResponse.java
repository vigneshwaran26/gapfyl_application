package com.gapfyl.dto.courses;

import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 06/08/21
 **/

@Getter
@Setter
public class CourseResponse {

    private String id;

    private String title;

    private String description;

    private String language;

    private String currencyCode;

    private String currencySymbol;

    private Double amount;

    private String thumbnailUrl;

    private String previewUrl;

}
