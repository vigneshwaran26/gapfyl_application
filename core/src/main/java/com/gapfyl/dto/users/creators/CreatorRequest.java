package com.gapfyl.dto.users.creators;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class CreatorRequest {

    @NotBlank
    public String about;

    @NotEmpty
    public List<String> expertise;

    @NotEmpty
    public List<String> interests;

    @NotEmpty
    public List<String> languages;

    public String linkedProfile;

    public String profileUrl;

    public List<Education> educations;

    public List<Work> works;

}