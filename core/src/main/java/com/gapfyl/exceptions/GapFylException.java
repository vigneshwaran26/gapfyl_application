package com.gapfyl.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

@Getter
@Setter
public class GapFylException extends Exception {
    private Integer code;
    private Object[] arguments;

    public GapFylException(Integer code, String message, Object[] arguments) {
        super(message);
        this.code = code;
        this.arguments = arguments;
    }

    public GapFylException(Integer code, String message) {
        this(code, message, new Object[] {});
    }
}
