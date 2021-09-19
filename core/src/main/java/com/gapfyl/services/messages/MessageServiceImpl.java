package com.gapfyl.services.messages;

import com.gapfyl.dto.messages.MessageRequest;
import com.gapfyl.exceptions.messages.ReceiverNotFoundException;
import com.gapfyl.exceptions.messages.SenderNotFoundException;
import com.gapfyl.models.messages.MessageEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.MessageRepository;
import com.gapfyl.services.users.UserService;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    private MessageRequest entityToDTO(MessageEntity messageEntity){
        MessageRequest messageRequestDTO = new MessageRequest();
        messageRequestDTO.setId(messageEntity.getId());
        messageRequestDTO.setSender(messageEntity.getSender().getId());
        messageRequestDTO.setReceiver(messageEntity.getReceiver().getId());
        messageRequestDTO.setMessage(messageEntity.getMessage());
        messageRequestDTO.setReadAt(messageEntity.getReadAt());
        messageRequestDTO.setCreatedDate(messageEntity.getCreatedDate());
        messageRequestDTO.setModifiedDate(messageEntity.getModifiedDate());
        return messageRequestDTO;
    }

    @Override
    public MessageRequest sendMessage(MessageRequest messageRequestDTO, UserEntity userEntity) throws SenderNotFoundException,
            ReceiverNotFoundException {

        log.info("user {} [{}] sending MessageRequest", userEntity.getName(), userEntity.getId());
        UserEntity sender = userService.fetchUserById(messageRequestDTO.getSender());
        if (sender == null) {
            log.error("sender {} not found", messageRequestDTO.getSender());
            throw new SenderNotFoundException(messageRequestDTO.getId());
        }

        UserEntity receiver = userService.fetchUserById(messageRequestDTO.getReceiver());
        if (receiver == null) {
            log.error("receiver {} not found", messageRequestDTO.getReceiver());
            throw new ReceiverNotFoundException(messageRequestDTO.getReceiver());
        }

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(sender);
        messageEntity.setReceiver(receiver);
        messageEntity.setMessage(messageRequestDTO.getMessage());
        messageEntity.setReadAt(messageRequestDTO.getReadAt());
        messageEntity.setCreatedDate(Common.getCurrentUTCDate());
        messageEntity.setModifiedDate(Common.getCurrentUTCDate());
        messageEntity.setCreatedBy(userEntity);
        messageEntity.setModifiedBy(userEntity);
        messageEntity = messageRepository.save(messageEntity);
        log.info("user {} [{}] sent MessageRequest", userEntity.getName(), userEntity.getId());
        return entityToDTO(messageEntity);
    }

    @Override
    public List<MessageRequest> fetchMessages(UserEntity userEntity) {
        List<MessageEntity> messages = messageRepository.fetchMessages(userEntity);
        return messages.stream().map(item -> entityToDTO(item)).collect(Collectors.toList());
    }

    @Override
    public void updateReadAt(String messageId, UserEntity userEntity) {
        UpdateResult updateResult = messageRepository.updateReadAt(messageId, userEntity);
        if (updateResult.getMatchedCount() == 0) {
            log.error("");
        }

        if (updateResult.getModifiedCount() == 0) {
            log.error("failed to update message {} read", messageId);
        }
    }
}
