package com.example.Note.Book;

import com.example.Note.Book.model.Note;
import com.example.Note.Book.model.User;
import com.example.Note.Book.service.NoteService;
import com.example.Note.Book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class NoteBookApplication {
	@Autowired
	private static UserService userService;

	private static NoteService noteService;

	public NoteBookApplication(UserService userService, NoteService noteService){
		this.userService=userService;
		this.noteService=noteService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NoteBookApplication.class, args);

		/*User user=new User();
		user.setUserName("sam3238");
		user.setPassword("password123");
		user.setName("samer");
		user.setCreationDate(LocalDate.now());

		userService.createUser(user);*/

		/*Note note=new Note();
		note.setText("Hello this is my first note ever!!!!!");
		User user=userService.getUserById(1);
		note.setUser(user);

		noteService.createNote(note);*/


	}

}
