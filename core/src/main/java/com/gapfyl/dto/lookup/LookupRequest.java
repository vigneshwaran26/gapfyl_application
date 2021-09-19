package com.gapfyl.dto.lookup;

import com.gapfyl.enums.common.LookupFilterCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

@Getter
@Setter
public class LookupRequest {

    @NotNull
    private LookupFilterCode lookupFilterCode;

    private String lookupMapFilterKey;

    private String lookupMapFilterValue;

    @NotBlank
    private String lookupFilterKey;

    @NotBlank
    private String lookupFilterValue;

}
