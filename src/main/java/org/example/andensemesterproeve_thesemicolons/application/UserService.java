package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        Optional<String> retrievedPassword = userRepository.findPasswordByUsername(user.getUsername());
        if (retrievedPassword.isEmpty()) {
            return null;
        }

        if (BCrypt.checkpw(user.getPassword(), retrievedPassword.get())) {
            Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());

            if (userFromDb.isEmpty()) {
                return null;
            }
            User foundUser = userFromDb.get();
            fillUserDecksWithTheirCards(foundUser);

            return foundUser;
        }

        return null;
    }

    public boolean createUser(User user) {
        try {
            user.validateValues();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return false;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        //Users are created as "Spiller" per default. Upgrading their user type has to be approved and done by an Admin later on
        userRepository.createStandardUser(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public List<Title_ENUM> getAllTitles() {
        return userRepository.findAllUniqueTitles();
    }

    public List<User> filterUsersByTitle(String title) {
        if (Title_ENUM.Spiller.name().equals(title)) {
            return userRepository.findAllUsersByTitle(Title_ENUM.Spiller);
        }
        if (Title_ENUM.Arrangoer.name().equals(title)) {
            return userRepository.findAllUsersByTitle(Title_ENUM.Arrangoer);
        }
        if (Title_ENUM.Admin.name().equals(title)) {
            return userRepository.findAllUsersByTitle(Title_ENUM.Admin);
        }
        return new ArrayList<>();
    }

    public void adminEditUser(User user) {
        userRepository.adminEditUser(user);
    }

    public User adminFindUserByUsername(String username) {
        Optional<User> usernameUser = userRepository.adminFindUserByUsername(username);

        if (usernameUser.isEmpty()) {
            return null;
        }

        return usernameUser.get();
    }

    private void fillUserDecksWithTheirCards(User user) {
        for (int i = 0; i < user.getDecks().size(); i++) {
            List<Integer> cardIds = userRepository.findCardIdsForDeck(user.getDecks().get(i));
            fillDeckWithCardsFromCardIdList(user, user.getDecks().get(i), cardIds);
        }
    }

    private void fillDeckWithCardsFromCardIdList(User user, Deck deck, List<Integer> cardIds) {
        for (Integer cardId : cardIds) {
            for (int j = 0; j < user.getCards().size(); j++) {
                if (cardId == user.getCards().get(j).getId()) {
                    deck.addCard(user.getCards().get(j));
                    j = user.getCards().size();
                }
            }
        }
    }
}