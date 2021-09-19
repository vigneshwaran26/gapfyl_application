package com.gapfyl.models.notifications;

import com.gapfyl.models.users.UserEntity;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class ReadBy {

    @Field("reader")
    private UserEntity reader;

    @Field("read_at")
    private Date readAt;
}
