package com.dockeep.user;

import com.dockeep.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User findEntityById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow();
    }

}
