package com.chat.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author
 */
@Entity
@Table(name = "usr")
@Data
public class User {
    @Id
    private String id;
    private String title;
}
