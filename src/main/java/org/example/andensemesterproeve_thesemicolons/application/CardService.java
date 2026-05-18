package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.repository.ICardRepository;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final ICardRepository cardRepository;

    public CardService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public List<String> getAllSets() {
        return cardRepository.findAllUniqueSetAbbreviations();
    }

    public List<CardType_ENUM> getAllTypes() {
        return cardRepository.findAllUniqueTypes();
    }
}
