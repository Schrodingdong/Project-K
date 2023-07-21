package com.schrodingdong.usermanagerservice.serivce;

import com.schrodingdong.usermanagerservice.model.UserModel;
import com.schrodingdong.usermanagerservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final Neo4jTemplate neo4jTemplate;

    public UserModel createUser(String email, String username, String bio) {
        return userRepository.save(new UserModel(email, username, bio));
    }
    public void deleteUser(String email) throws IllegalArgumentException{
        userRepository.deleteById(email);
    }
    public UserModel updateUser(String email, String username, String bio) throws NoSuchElementException, IllegalArgumentException {
        UserModel user = userRepository.findById(email).get();
        if (username != null && !username.isBlank()) user.setUsername(username);
        if (bio != null) user.setBio(bio);
        UserModel newUser = userRepository.save(user);
        return newUser;
    }
    public UserModel getUser(String email) throws NoSuchElementException, IllegalArgumentException{
        return userRepository.findById(email).get();
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean userExists(String email) {
        return userRepository.existsById(email);
    }

    public UserModel followUser(String email, String followEmail) throws NoSuchElementException, IllegalArgumentException{
        if (email.equals(followEmail)) throw new IllegalArgumentException("Unable to follow yourself");
        UserModel user = userRepository.findById(email)
                .orElseThrow(() -> new NoSuchElementException("User of email : '"+ email +"' not found") );
        UserModel followUser = userRepository.findById(followEmail)
                .orElseThrow(() -> new NoSuchElementException("User of email : '"+ followEmail +"' not found") );
        user.getFollowing().add(followUser);
        return userRepository.save(user);
    }
    public void unfollowUser(String email, String toUnfollow) throws NoSuchElementException{
        if (!userRepository.existsById(email)) throw new NoSuchElementException("User of email : '"+ email +"' not found");
        if (!userRepository.existsById(toUnfollow)) throw new NoSuchElementException("User of email : '"+ toUnfollow +"' not found");
        userRepository.deleteFollowBetween(email, toUnfollow);
    }
}
