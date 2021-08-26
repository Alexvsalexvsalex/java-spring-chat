package com.chat.domain.dto;

import com.chat.domain.Message;
import com.chat.domain.User;

/**
 * @author
 */
public class MessageDto {
    private Long id;
    private String text;
    private User author;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.text = message.getText();
        this.author = message.getAuthor();
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
}
