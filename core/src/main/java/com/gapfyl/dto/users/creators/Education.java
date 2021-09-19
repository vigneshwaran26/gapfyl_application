package com.gapfyl.dto.users.creators;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

@Getter
@Setter
public class Education {

    @NotBlank
    public String study;

    @NotBlank
    public String school;
}

