package com.gapfyl.repository.custom;

import com.gapfyl.models.events.EventEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

public interface ICustomEventRepository {

    List<EventEntity> fetchEvents(UserEntity userEntity);

}
