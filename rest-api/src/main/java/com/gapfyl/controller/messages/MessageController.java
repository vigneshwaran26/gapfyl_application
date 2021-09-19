package com.gapfyl.controller.messages;


import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.messages.MessageRequest;
import com.gapfyl.exceptions.messages.ReceiverNotFoundException;
import com.gapfyl.exceptions.messages.SenderNotFoundException;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.messages.MessageService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0/messages")
public class MessageController extends AbstractController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    ResponseEntity sendMessage(@RequestBody @Valid MessageRequest messageRequestDTO) throws
            SenderNotFoundException, ReceiverNotFoundException {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(messageService.sendMessage(messageRequestDTO, userEntity));
    }

    @GetMapping("/fetch/received")
    ResponseEntity receivedMessages() {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(messageService.fetchMessages(userEntity));
    }

    @PutMapping("/update/{messageId}/read")
    ResponseEntity updateMessageRead(@PathVariable("messageId") String messageId) {
        UserEntity userEntity = getCurrentUser();
        messageService.updateReadAt(messageId, userEntity);
        return ResponseEntity.ok(ImmutableMap.builder().put("success", true).build());
    }
}
