package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    public Optional<User> findById(int id);
}
