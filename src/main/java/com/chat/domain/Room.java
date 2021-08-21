package com.chat.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 */
@Entity
@Table(name = "chat")
public class Room {
    private static long roomCounter = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private final String title;

    public Room(String title) {
        this.id = roomCounter++;
        this.title = title;
    }

    public Room() {
        id = 12345L;
        title = "something went wrong";
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
