package com.gapfyl.dto.events;

import com.gapfyl.dto.common.Base;
import com.gapfyl.models.events.EventType;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Event extends Base {

    private String id;

    @NotNull
    private EventType eventType;

    @NotBlank
    private String eventValue;

    @NotBlank
    private String sourcePage;

}

