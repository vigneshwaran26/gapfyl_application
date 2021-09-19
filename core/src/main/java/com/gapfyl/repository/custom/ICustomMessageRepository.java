package com.gapfyl.repository.custom;

import com.gapfyl.models.messages.MessageEntity;
import com.gapfyl.models.users.UserEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface ICustomMessageRepository {

    List<MessageEntity> fetchMessages(UserEntity userEntity);

    UpdateResult updateReadAt(String messageId, UserEntity userEntity);
}
