package com.gapfyl.dto.errors;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

@Setter
@Getter
public class ErrorResponseDTO {
    private List<ErrorMessageDTO> errors;

    public ErrorResponseDTO(List<ErrorMessageDTO> errors) {
        this.errors = errors;
    }
}
