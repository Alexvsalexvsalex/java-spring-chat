package com.chat.domain.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chat.domain.Message;
import com.chat.domain.User;

/**
 * @author
 */
public class MessageFilteredDto {
    private final Long id;
    private final String username;
    private final String message;

    public MessageFilteredDto(Long id, String username, String message) {
        this.id = id;
        this.username = username;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
}
