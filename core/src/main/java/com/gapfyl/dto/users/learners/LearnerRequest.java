package com.gapfyl.dto.users.learners;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author vignesh
 * Created on 14/08/21
 **/

@Getter
@Setter
public class LearnerRequest {

    @NotBlank
    public String about;

    public List<String> interests;

    public List<String> languages;

}
