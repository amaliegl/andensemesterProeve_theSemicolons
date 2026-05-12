package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        Optional<User> user = userRepository.findById(6); //TODO
        if (user.isEmpty()) {
            return null;
        }
        /*
        User loggedInUser = user.get();
        if (BCrypt.checkpw(password, loggedInUser.getPassword())) {
            return loggedInUser;
        }*/
        return null;
    }
}