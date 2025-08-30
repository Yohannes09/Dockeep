package com.dockeep;

import com.authmat.tool.events.PublicKeyRotationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
@Slf4j
public class PublicKeyManager {
    private static final int MAX_KEYS_TRACED = 20;

    private final Deque<String> publicKeyInsertionSequence = new ConcurrentLinkedDeque<>();
    private final Map<String,String> publicKeyRepository = new ConcurrentHashMap<>();


    @KafkaListener(topics = "token-rotation-event", groupId = "authmat-service")
    public void publicKeyListener(PublicKeyRotationEvent event){
        if(publicKeyInsertionSequence.size() >= MAX_KEYS_TRACED){
            String key = publicKeyInsertionSequence.poll();
            publicKeyRepository.remove(key);
        }

        publicKeyInsertionSequence.addLast(event.publicKey());
        publicKeyRepository.put(event.kid(), event.publicKey());
    }

    public Collection<String> getKey(){
        return Collections.unmodifiableCollection(publicKeyInsertionSequence);
    }

    public Optional<String> findByKid(String kid){
        return Optional.of(publicKeyRepository.getOrDefault(kid, null));
    }

}
