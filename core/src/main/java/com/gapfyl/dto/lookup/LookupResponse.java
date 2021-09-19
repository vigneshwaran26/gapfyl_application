package com.gapfyl.dto.lookup;

import com.gapfyl.dto.common.Base;
import com.gapfyl.enums.common.LookupFilterCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 06/07/21
 **/

@Getter
@Setter
public class LookupResponse {

    public LookupFilterCode lookupFilterCode;

    public String lookupFilterKey;

    public String lookupFilterValue;

    public String lookupMapFilterKey;

    public String lookupMapFilterValue;

    public Boolean isActive;

}
