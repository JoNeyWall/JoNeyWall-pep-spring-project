package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

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

    /**
     * Deletes message by its id and returns the number of rows updated
     * @param messageId
     * @return number of rows updated
     */
    int deleteMessageByMessageId(Integer messageId);

    /**
     * Retrieve messages by accountId
     * @param postedBy
     * @return list of messages postedBy a particular account.
     */
    List<Message> findMessagesByPostedBy(Integer postedBy);
}
