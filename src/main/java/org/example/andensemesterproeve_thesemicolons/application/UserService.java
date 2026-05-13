package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        try {
            int userId = userRepository.findIdByUsernameAndPassword(username, BCrypt.hashpw(password, BCrypt.gensalt()));

            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return null;
            }
            User foundUser = user.get();
            fillUserDecksWithTheirCards(foundUser);

            return foundUser;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private void fillUserDecksWithTheirCards(User user) {
        for (int i = 0; i < user.getDecks().size(); i++) {
            userRepository.findCardIdsForDeck(user.getDecks().get(i));
        }
    }
}