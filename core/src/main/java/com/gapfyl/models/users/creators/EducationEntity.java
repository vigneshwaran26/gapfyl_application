package com.gapfyl.models.users.creators;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

@Getter
@Setter
public class EducationEntity {

    @Field("study")
    public String study;

    @Field("school")
    public String school;

}
