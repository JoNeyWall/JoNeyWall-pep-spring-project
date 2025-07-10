package com.example.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.entity.*;
import com.example.repository.*;
import com.example.exception.MessageCreationException;

/**
 * This class is responsible for message related services and calls to both accountRepository and messageRepository.
 */
@Service
@Transactional
public class MessageService {
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    //Service Methods

    /**
     * This method handles the input validation and persistence of new messages.
     * 
     * @param message
     * @return
     * @throws MessageCreationException
     */
    public Message postMessage(Message message) throws MessageCreationException{
        //Input validation
        if (message.getMessageText().length() > 255){
            throw new MessageCreationException("Message cannot be longer than 255 characters.");
        }
        if (message.getMessageText().isBlank()){
            throw new MessageCreationException("Message cannot be blank.");
        }
        if (!accountRepository.getAccountByAccountId(message.getPostedBy()).isPresent()){
            throw new MessageCreationException("Messages must be posted by verified users. The user posting the message does not exist.");
        }

        //Input is valid
        return messageRepository.save(new Message(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch()));
    }

    /**
     * This method handles the retrieval of all existing messages.
     * @return List of all messages within the database.
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();  
    }

    /**
     * This method handles the retreival of a message given its id.
     * @param messageId
     * @return message matching the provided id
     */
    public Message getMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findMessageByMessageId(messageId);
        if (optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }

    /**
     * This method handles the deletion of a message given its id.
     * @param messageId
     * @return number of rows updated as a result of this call
     */
    public int deleteMessageById(Integer messageId){
        int rowsUpdated = messageRepository.deleteMessageByMessageId(messageId);
        return rowsUpdated;
    }

    /**
     * This method handles the updating of a message given its id and new message text.
     * @param messageId
     * @param message
     * @throws MessageCreationException
     * @return number of rows updated
     */
    public int updateMessageById(Integer messageId, Message message) throws MessageCreationException{
        //Input text validation
        if (message.getMessageText().length() > 255){
            throw new MessageCreationException("Message cannot be longer than 255 characters.");
        }
        if (message.getMessageText().isBlank()){
            throw new MessageCreationException("Message cannot be blank.");
        }
        
        //Updated message
        Optional<Message> optionalMessage = messageRepository.findMessageByMessageId(messageId);
        if (optionalMessage.isPresent()){
            Message messageToUpdate = optionalMessage.get();
            messageToUpdate.setMessageText(message.getMessageText());
            messageRepository.save(messageToUpdate);
            return 1;
        }

        //Nothing to update
        return 0;
    }

    /**
     * This method handles the retrival of messages written by a particular user denoted by postedBy.
     * @param accountId
     * @return list of messages written by specified account
     */
    public List<Message> getAllMessagesByAccount(Integer accountId){
        List<Message> messages = messageRepository.findMessagesByPostedBy(accountId);
        return messages;
    }
}
