package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.example.entity.*;
import com.example.service.*;
import com.example.exception.*;

/**
 * This class is responsible for performing required actions upon receiving an http request and sending appropriate http responses.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //Endpoints

    /**
     * This endpoint handles account registration via a POST to /register.
     * 
     * @param account not containing an ID
     * @return http response containing fully formed account.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account){
        try {
            Account newAccount = accountService.registerAccount(account);
            return ResponseEntity.status(200).body(newAccount);
        }
        catch(DuplicateUsernameException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
        catch(RegistrationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * This endpoint handles account logins via a POST to /login.
     * 
     * @param account not containing an ID
     * @return http response containing fully formed account.
     */
    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody Account account){
        try{
            Account verifiedAccount = accountService.verifyLogin(account);
            return ResponseEntity.status(200).body(verifiedAccount);
        }
        catch(AccountDoesNotExistException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    /**
     * This endpoint handles the posting of messages via a POST to /messages.
     * 
     * @param message not containing an ID
     * @return http response containing fully formed message
     */
    @PostMapping("/messages")
    public ResponseEntity<?> postMessage(@RequestBody Message message){
        try{
            Message postedMessage = messageService.postMessage(message);
            return ResponseEntity.status(200).body(postedMessage);
        }
        catch(MessageCreationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * This endpoint handles the retrival of all messages via a GET to /messages.
     * 
     * @return http response containing list of all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    /**
     * This endpoint handles the retrival of a message given its ID via a Get request to /messages/{messageId}
     * 
     * @param messageId
     * @return http response containing messages matching the id
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }

    /**
     * This endpoint handles the deletion of a message given its Id via a Delete request to /messages/{messageId}
     * 
     * @param messageId
     * @return http response containing the number of rows updated.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId){
        int rowsUpdated = messageService.deleteMessageById(messageId);
        if (rowsUpdated > 0){
            return ResponseEntity.status(200).body(rowsUpdated);
        }
        return ResponseEntity.status(200).body(null);
    }

    /**
     * This endpoint handles the updating of a message given its Id via a Patch request to /messages/{messageId}
     * 
     * @param messageId
     * @param message
     * @return http response containing the number of rows updated.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message){
        try{
            int rowsUpdated = messageService.updateMessageById(messageId, message);
            if (rowsUpdated > 0){
                return ResponseEntity.status(200).body(rowsUpdated);
            }
            return ResponseEntity.status(400).body(null);
        }
        catch(MessageCreationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getAllMessagesByAccount(@PathVariable Integer accountId){
        List<Message> messages = messageService.getAllMessagesByAccount(accountId);
        return ResponseEntity.status(200).body(messages);
    }
}
