package com.gapfyl.models.notifications;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.users.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@TypeAlias("notifications")
@Document(collection = Collections.NOTIFICATIONS)
@EqualsAndHashCode(callSuper = false)
public class NotificationEntity extends BaseAuditEntity {

    @Field("title")
    private String title;

    @Field("content")
    private String content;

    @Field("click_action")
    private String clickAction;

    @Field("type")
    private NotificationType type;

    @Field("sender")
    private UserEntity sender;

    @Field("receivers")
    private List<UserEntity> receivers;

    @Field("read_by")
    private List<ReadBy> readBy;
}
