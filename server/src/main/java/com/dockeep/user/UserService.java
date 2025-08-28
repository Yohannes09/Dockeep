package com.dockeep.user;

import com.authmat.tool.events.NewUserEvent;
import com.dockeep.user.model.User;
import com.dockeep.user.repository.CachedUserRepository;
import com.dockeep.user.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CachedUserRepository cachedUserRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @KafkaListener(topics = "user_created", groupId = "authmat-service")
    public void createUser(NewUserEvent newUserEvent){
        User user = mapper.eventToEntity(newUserEvent);
        cachedUserRepository.save(user);
    }

    public void serviceAcknowledgement(NewUserEvent newUserEvent){
        kafkaTemplate.send("user-created-events", newUserEvent);
    }

}
