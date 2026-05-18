package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.CardType_ENUM;

import java.util.List;

public interface ICardRepository {

    List<String> findAllUniqueSetAbbreviations();

    List<CardType_ENUM> findAllUniqueTypes();
}
