package com.gapfyl.services.messages;


import com.gapfyl.dto.messages.MessageRequest;
import com.gapfyl.exceptions.messages.ReceiverNotFoundException;
import com.gapfyl.exceptions.messages.SenderNotFoundException;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

public interface MessageService {

    MessageRequest sendMessage(MessageRequest messageRequestDTO, UserEntity userEntity) throws SenderNotFoundException,
            ReceiverNotFoundException;

    List<MessageRequest> fetchMessages(UserEntity userEntity);

    void updateReadAt(String messageId, UserEntity userEntity);

}
