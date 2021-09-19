package com.gapfyl.controller.common;

import com.gapfyl.dto.events.Event;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.events.EventService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/1.0/events")
public class EventController extends AbstractController {

    @Autowired
    EventService eventService;

    @PostMapping("")
    ResponseEntity createEvent(@RequestBody @Valid Event eventDTO){
        UserEntity userEntity =  getCurrentUser();
        eventService.createEvent(eventDTO, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true));
    }

    @GetMapping("")
    ResponseEntity userEvents() {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(eventService.fetchEvents(userEntity));
    }
}
