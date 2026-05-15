package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
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

    public boolean createUser(User user) {
        try {
            user.validateValues();
        } catch (IllegalArgumentException e) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        //Users are created as "Spiller" per default. Upgrading their user type has to be approved and done by an Admin later on
        userRepository.createStandardUser(user);
        return true;
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