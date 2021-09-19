package com.gapfyl.models.events;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.users.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = Collections.EVENTS)
@EqualsAndHashCode(callSuper = false)
public class EventEntity extends BaseAuditEntity {

    @Field("event_type")
    private EventType eventType;

    @Field("event_value")
    private String eventValue;

    @Field("source_page")
    private String sourcePage;

    @Field("event_time")
    private Date eventTime;

    @Field("user")
    private UserEntity user;
}
