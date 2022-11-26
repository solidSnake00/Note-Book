package com.example.Note.Book.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Username required")
    private String username;
    @Column(name = "password",nullable = false)
    @NotBlank(message = "password required")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> noteList;
}
