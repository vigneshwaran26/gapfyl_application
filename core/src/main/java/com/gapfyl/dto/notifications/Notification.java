package com.gapfyl.dto.notifications;

import com.gapfyl.dto.common.Base;
import com.gapfyl.models.notifications.NotificationType;
import com.gapfyl.models.notifications.ReadBy;
import com.gapfyl.models.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Notification extends Base {

    private String title;

    private String content;

    private String clickAction;

    private NotificationType type;

    private UserEntity sender;

    private List<UserEntity> receivers;

    private List<ReadBy> readBy;
}