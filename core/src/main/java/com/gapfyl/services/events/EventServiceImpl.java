package com.gapfyl.services.events;

import com.gapfyl.dto.events.Event;
import com.gapfyl.models.events.EventEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.EventRepository;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventServiceImpl<eventDTOS> implements EventService{

    @Autowired
    EventRepository eventRepository;

    public Event entityToDTO(EventEntity eventEntity){
        Event eventDTO = new Event();
        eventDTO.setId(eventEntity.getId());
        eventDTO.setEventType(eventEntity.getEventType());
        eventDTO.setEventValue(eventEntity.getEventValue());
        eventDTO.setSourcePage(eventEntity.getSourcePage());
            return eventDTO;
    }

    @Override
    public void createEvent(Event eventDTO, UserEntity userEntity) {
        EventEntity event = new EventEntity();
        event.setEventType(eventDTO.getEventType());
        event.setEventValue(eventDTO.getEventValue());
        event.setSourcePage(eventDTO.getSourcePage());
        event.setEventTime(Common.getCurrentUTCDate());
        event.setUser(userEntity);
        eventRepository.save(event);
    }

    @Override
    public List<Event> fetchEvents(UserEntity userEntity) {
        List<EventEntity> events = eventRepository.fetchEvents(userEntity);
        return events.stream().map(item -> entityToDTO(item)).collect(Collectors.toList());
    }
}

