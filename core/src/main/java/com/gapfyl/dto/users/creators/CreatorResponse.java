package com.gapfyl.dto.users.creators;

import com.gapfyl.models.users.creators.CreatorStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class CreatorResponse {

    public String id;

    public String about;

    public List<String> expertise;

    public List<String> interests;

    public List<String> languages;

    public List<Education> educations;

    public List<Work> works;

    public String linkedInUrl;

    public String profileUrl;

    public CreatorStatus status;
}
