package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.exceptions.DataAccessException;
import org.example.andensemesterproeve_thesemicolons.exceptions.InsufficientRightsException;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IUserRepository;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final ExceptionService exceptionService;

    public UserService(IUserRepository userRepository, ExceptionService exceptionService) {
        this.userRepository = userRepository;
        this.exceptionService = exceptionService;
    }

    public User login(User user) {
        try {
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
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public boolean createUser(User user) {
        try {
            user.validateValues();
        } catch (IllegalArgumentException e) {
            exceptionService.logException(e);
            return false;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        try {
            //Users are created as "Spiller" per default. Upgrading their user type has to be approved and done by an Admin later on
            userRepository.createStandardUser(user);
            return true;
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAllUsers();
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Title_ENUM> getAllTitles() {
        return userRepository.findAllUniqueTitles();
    }

    public List<User> filterUsersByTitle(String title) {
        try {
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
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public void adminEditUser(User admin, User userToEdit) {
        try {
            if (admin.getTitle() == Title_ENUM.Admin) {
                userRepository.adminEditUser(userToEdit);
            } else {
                throw new InsufficientRightsException("User " + admin + " attempting to access adminEditUser() without Admin rights");
            }
        } catch (DataAccessException | InsufficientRightsException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public User adminFindUserByUsername(User admin, String username) {
        try {
            if (admin.getTitle() == Title_ENUM.Admin) {
                Optional<User> usernameUser = userRepository.adminFindUserByUsername(username);

                return usernameUser.orElse(null);

            } else {
                throw new InsufficientRightsException("User " + admin + " attempting to access adminFindUserByUsername() without Admin rights");
            }
        } catch (DataAccessException | InsufficientRightsException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public User refreshSessionUser(User sessionUser) {
        try {
            Optional<User> user = userRepository.findByUsername(sessionUser.getUsername());

            if (user.isEmpty()) {
                return null;
            }
            User foundUser = user.get();
            fillUserDecksWithTheirCards(foundUser);

            return foundUser;
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    private void fillUserDecksWithTheirCards(User user) {
        try {
            for (int i = 0; i < user.getDecks().size(); i++) {
                List<Integer> cardIds = userRepository.findCardIdsForDeck(user.getDecks().get(i));
                fillDeckWithCardsFromCardIdList(user, user.getDecks().get(i), cardIds);
            }
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
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