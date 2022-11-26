package com.example.Note.Book.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "note")
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "updated")
    private Boolean updated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
