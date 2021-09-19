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
public class Work {

    @NotBlank
    public String job;

    @NotBlank
    public String company;
}
