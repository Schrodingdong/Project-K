package com.schrodingdong.usermanagerservice.serivce;

import com.schrodingdong.usermanagerservice.model.UserModel;
import com.schrodingdong.usermanagerservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
