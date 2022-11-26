package com.example.Note.Book.service;

import com.example.Note.Book.model.Note;
import com.example.Note.Book.model.User;
import com.example.Note.Book.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.*;

@Service
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Autowired
    private final UserService userService;

    DateTimeFormatter formatter = ISO_LOCAL_DATE_TIME;

    String now;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    public Note getNoteById(long id){
        Optional<Note> optionalNote=noteRepository.findById(id);
        return optionalNote.get();
    }


    public void createNote(Note note, long id){
        now=LocalDateTime.now().format(formatter);
        note.setCreationDate(LocalDateTime.parse(now));
        note.setUpdated(false);
        note.setUser(userService.getUserById(id));

        noteRepository.save(note);
    }

    public void updateNote(Note note, String text){
        now=LocalDateTime.now().format(formatter);
        note.setText(text);
        note.setUpdated(true);
        note.setUpdateDate(LocalDateTime.parse(now));

        noteRepository.save(note);
    }
    public void updateNote(Note note){
        now=LocalDateTime.now().format(formatter);
        note.setUpdated(true);
        note.setUpdateDate(LocalDateTime.parse(now));

        noteRepository.save(note);
    }

    public void deleteNote(long id){
        Note note=this.getNoteById(id);
        noteRepository.delete(note);
    }

    public List<Note> getAllNotesByUser(long id){
        User user=userService.getUserById(id);
        return noteRepository.findAllNotesByUser(user);
    }
}
