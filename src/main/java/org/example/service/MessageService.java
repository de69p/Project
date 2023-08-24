package org.example.service;

import org.example.dao.MessageDAO;
import org.example.model.Message;

import java.sql.SQLException;

import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) throws SQLException {
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.findAll();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.findById(messageId);
    }

    public List<Message> findByUserId(int userId) {
        return messageDAO.findByUserId(userId);
    }

    public Message deleteMessage(int messageId) {
        Message deletedMessage = getMessageById(messageId);
    
        if (deletedMessage != null) {
            boolean success = messageDAO.deleteMessage(messageId);
            if (success) {
                return deletedMessage;
            }
        }
    
        return null;
    }

    public Message updateMessage(int messageId, String updatedMessageText) {
        return messageDAO.updateMessage(messageId, updatedMessageText);
    }

    public List<Message> findByAccountId(int accountId) {
        return messageDAO.findByAccountId(accountId);
    }
    
}