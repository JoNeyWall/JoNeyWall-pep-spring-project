package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

/**
 * JPARepository to be accessed by MessageService.java
 */
public interface MessageRepository extends JpaRepository<Message, Long>{
    /**
     * Retrieve message by its id
     * @param messageId
     * @return message with matching id
     */
    Optional<Message> findMessageByMessageId(Integer messageId);
}
