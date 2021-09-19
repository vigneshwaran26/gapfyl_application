package com.gapfyl.models.messages;

import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.users.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@TypeAlias("messages")
@Document(collection = "messages")
@EqualsAndHashCode(callSuper = false)
public class MessageEntity extends BaseAuditEntity {

    @DBRef
    @Field("sender")
    private UserEntity sender;

    @DBRef
    @Field("receiver")
    private UserEntity receiver;

    @Field("message")
    private String message;

    @Field("read_at")
    private Date readAt;
}
