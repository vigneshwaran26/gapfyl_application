package com.gapfyl.dto.messages;

import com.gapfyl.dto.common.Base;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class MessageRequest extends Base {

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;

    @NotBlank
    private String message;

    private Date readAt;

}


