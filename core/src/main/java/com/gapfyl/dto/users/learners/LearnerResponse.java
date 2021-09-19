package com.gapfyl.dto.users.learners;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 15/08/21
 **/

@Getter
@Setter
public class LearnerResponse {

    public String id;

    public String about;

    public List<String> interests;

    public List<String> languages;

}
