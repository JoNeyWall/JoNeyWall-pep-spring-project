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


    public Message getMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findMessageByMessageId(messageId);
        if (optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }
}
