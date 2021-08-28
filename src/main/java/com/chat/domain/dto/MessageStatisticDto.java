package com.chat.domain.dto;

/**
 * @author
 */
public class MessageStatisticDto {
    private String username;
    private Long count;

    public MessageStatisticDto(String username, Long count) {
        this.username = username;
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
