package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.repository.ExceptionRepositoryMySql;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {
    private final ExceptionRepositoryMySql exceptionRepository;

    public ExceptionService(ExceptionRepositoryMySql exceptionRepository) {
        this.exceptionRepository = exceptionRepository;
    }

    public void logException(Exception exception) {
        exceptionRepository.logExceptionInDatabase(exception);
    }
}
