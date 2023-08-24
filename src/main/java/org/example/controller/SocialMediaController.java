package org.example.controller;


import io.javalin.Javalin;
import io.javalin.http.Context;

import org.example.dao.MessageDAO;
import org.example.model.Account;
import org.example.model.Message;
import org.example.service.AccountService;
import org.example.service.MessageService;

import java.sql.SQLException;
import java.util.List;
// import DAO.AccountDAO;
// import java.sql.SQLException;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
        }

        //build no-args constructor
        public SocialMediaController() {
        }
     

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // app.get("example-endpoint", this::exampleHandler);

        app.get("/messages", this::getAllMessagesHandler);
        // app.get("/messages/:message_id", this::getMessageByIdHandler);
        // app.get("/accounts/:account_id/messages", this::getMessagesByAccountIdHandler);
        // app.patch("/messages/:message_id", this::updateMessageHandler);
        // app.delete("/messages/:message_id", this::deleteMessageHandler);
        app.post("/messages", this::createMessageHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }
// ************************************ REGISTER ***************************************
    private void registerHandler(Context context) {
        
    // Instantiate and get data
    // UserAccountService userAccountService = new UserAccountService();
    AccountService accountService = new AccountService();
    Account account = context.bodyAsClass(Account.class);

    //Validate
            if (account.getUsername() == null || account.getPassword().length()<4 || account.getPassword() == null) {
                context.status(400).json("");
                return;
            }

    // DOES ACCOUNT ALREADY EXIST? 400 RESPONSE
        Account existingAccount = accountService.retrieveAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            context.status(400).json("");
            return;
        }

    // CREATE! 200 RESPONSE
        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            context.status(200).json(createdAccount);
        }
        else {
            context.status(400).json("");
        }
    }

// ************************************ LOGIN ************************************
    private void loginHandler(Context context) {
        // UserAccountService userAccountService = new UserAccountService();
        Account account = context.bodyAsClass(Account.class);

        Account existingAccount = accountService.retrieveAccountByUsername(account.getUsername());

        //if login matched existing account, account json returned with account_id and 200 ok response 
       if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            context.status(200).json(existingAccount);
        } else {
            context.status(401).json("");
        }
    }

// ************************************ CREATE MESSAGE ***************************
    private void createMessageHandler(Context context) throws SQLException {
    // UserMessageService userMessageService = new UserMessageService();
    Message message = context.bodyAsClass(Message.class);


    if (message.getMessage_text() == null || message.getMessage_text().isEmpty()) {
        context.status(400).json("");
        return;
    }
    
    Message createdMessage = messageService.createMessage(message);
    if (createdMessage != null) {
        context.status(201).json(createdMessage);
    } else {
        context.status(400).json("");
    }
}
// ************************************ GET ALL MESSAGES *************************
    private void getAllMessagesHandler(Context context) {
        MessageDAO messageDAO = new MessageDAO();
        MessageService messageService = new MessageService(messageDAO);

    // get all messages json
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
}

// ************************************ GET ALL MESSAGES BY ACCOUNT ID************
    private void getMessagesByAccountIdHandler(Context context) {
        // get message by account ID josn
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.findByAccountId(accountId);
        context.json(messages);
    }
// ************************************ GET ALL MESSAGES BY MESSAGE ID************
    private void getMessageByIdHandler(Context context) {
    // get message by message ID json
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
            if (message != null) {
                context.json(message);
            } else {
                context.status(404);
    }
}
// ************************************ DELETE MESSAGES **************************
    private void deleteMessageHandler(Context context) {
        // delete message and retur json of deleted message!
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if (deletedMessage != null) {
            context.json(deletedMessage);
        } else {
            context.status(404);
        }
    }
// ************************************ UPDATE MESSAGES **************************
    private void updateMessageHandler(Context context) {
        // update the message return json or 404 response YAY! 
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = context.bodyAsClass(Message.class);
        Message result = messageService.updateMessage(messageId, updatedMessage.getMessage_text());
        if (result != null) {
            context.json(result);
        } else {
            context.status(404);
        }
    }

}