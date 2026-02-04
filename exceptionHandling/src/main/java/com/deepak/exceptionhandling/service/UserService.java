package com.deepak.exceptionhandling.service;

import com.deepak.exceptionhandling.dto.UserRequest;
import com.deepak.exceptionhandling.entity.User;
import com.deepak.exceptionhandling.exceptions.UserNotFoundException;
import com.deepak.exceptionhandling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserRequest userRequest) {
        User user = User.build(0,userRequest.getUsername(),userRequest.getEmail(),userRequest.getMobile(),userRequest.getGender(),userRequest.getAge(),
                userRequest.getNationality());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    return new UserNotFoundException("user not found with given id");
                });
    }
}
