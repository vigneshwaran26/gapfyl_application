package com.gapfyl.services.events;


import com.gapfyl.dto.events.Event;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

public interface EventService {

    void createEvent(Event eventDTO, UserEntity userEntity);

    List<Event> fetchEvents(UserEntity userEntity);

}


