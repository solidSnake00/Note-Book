package com.example.Note.Book.repository;

import com.example.Note.Book.model.Note;
import com.example.Note.Book.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    public List<Note> findAllNotesByUser(User user);
}