package com.gapfyl.dto.errors;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

@Getter
@Setter
public class ErrorMessageDTO {
    private Integer code;
    private String message;

    public ErrorMessageDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
