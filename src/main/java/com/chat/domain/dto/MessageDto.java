package com.chat.domain.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chat.domain.Message;
import com.chat.domain.User;

/**
 * @author
 */
public class MessageDto {
    private Long id;
    private String text;
    private User author;
    private String date;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.author = message.getAuthor();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = formater.format(message.getDate());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
