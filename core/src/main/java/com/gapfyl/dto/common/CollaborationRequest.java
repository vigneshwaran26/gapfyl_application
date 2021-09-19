package com.gapfyl.dto.common;

import com.gapfyl.enums.common.CreatorAccess;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author vignesh
 * Created on 08/07/21
 **/

@Getter
@Setter
public class CollaborationRequest {

    @NotBlank
    public String requesterId;

    @NotBlank
    public String collaboratorId;

    @NotEmpty
    public List<CreatorAccess> accesses;
}
