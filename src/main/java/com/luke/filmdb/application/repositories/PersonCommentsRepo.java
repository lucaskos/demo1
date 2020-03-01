package com.luke.filmdb.application.repositories;

import com.luke.filmdb.application.model.comments.PersonComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonCommentsRepo extends JpaRepository<PersonComment, Long> {
    Optional<List<PersonComment>> findByPersonId(@Param("personId") Long personId);
}