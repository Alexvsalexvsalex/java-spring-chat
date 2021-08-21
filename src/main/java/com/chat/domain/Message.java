package com.chat.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author
 */
@Entity(name = "message")
public class Message {
    private static long messageCounter = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long authorId;

    public Message() {}
    public Message(String title) {
        this.title = title;
    }

    private  String title;
    private long roomId;

//    public Message(long authorId, String title, long roomId) {
////        id = messageCounter++;
//        this.authorId = messageCounter++;
//        this.title = title;
//        this.roomId = messageCounter++;
//    }

//    public Message() {
//        id = 12345;
//        authorId = -1;
//        title = "";
//        roomId = -1;
//    }
}
