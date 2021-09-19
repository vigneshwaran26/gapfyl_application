package com.gapfyl.services.notifications;

import com.gapfyl.dto.notifications.Notification;
import com.gapfyl.models.notifications.NotificationEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.NotificationRepository;
import com.gapfyl.services.users.UserService;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements  NotificationService{

    @Autowired
    UserService userService;

    @Autowired
    NotificationRepository notificationRepository;

    private Notification entityToDTO(NotificationEntity notificationEntity){
        Notification notificationDTO = new Notification();
        notificationDTO.setId(notificationEntity.getId());
        notificationDTO.setTitle(notificationEntity.getTitle());
        notificationDTO.setContent(notificationEntity.getContent());
        notificationDTO.setType(notificationEntity.getType());
        notificationDTO.setClickAction(notificationEntity.getClickAction());
        notificationDTO.setSender(notificationEntity.getSender());
        notificationDTO.setReceivers(notificationEntity.getReceivers());
        notificationDTO.setReadBy(notificationEntity.getReadBy());
        notificationDTO.setCreatedDate(notificationEntity.getCreatedDate());
        notificationDTO.setModifiedDate(notificationEntity.getModifiedDate());
        return notificationDTO;

    }

    @Override
    public Notification createNotification(Notification notificationDTO, UserEntity userEntity) {
        log.info("user {} [{}] creating notification", userEntity.getName(), userEntity.getId());
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setTitle(notificationDTO.getTitle());
        notificationEntity.setContent(notificationDTO.getContent());
        notificationEntity.setType(notificationDTO.getType());
        notificationEntity.setClickAction(notificationDTO.getClickAction());
        notificationEntity.setSender(notificationDTO.getSender());
        notificationEntity.setReceivers(notificationDTO.getReceivers());
        notificationEntity.setReadBy(notificationDTO.getReadBy());
        notificationEntity.setCreatedDate(Common.getCurrentUTCDate());
        notificationEntity.setModifiedDate(Common.getCurrentUTCDate());
        notificationEntity.setCreatedBy(userEntity);
        notificationEntity.setModifiedBy(userEntity);
        notificationEntity = notificationRepository.save(notificationEntity);
        log.info("user {} [{}] created notification {}", userEntity.getName(), userEntity.getId());

        return entityToDTO(notificationEntity);

    }

    @Override
    public List<Notification> fetchUserNotifications(UserEntity userEntity) {
        List<NotificationEntity> notifications = notificationRepository.fetchNotifications(userEntity);
        return notifications.stream().map(item -> entityToDTO(item)).collect(Collectors.toList());
    }
}
