package com.chat.repository;

import com.chat.domain.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * @author
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
}
