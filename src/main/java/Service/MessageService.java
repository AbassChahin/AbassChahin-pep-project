package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    // Constructor which creates new messageDAO
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    // Constructor if MessageDAO given
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Add message after conditions met
    public Message addMessage(Message message) {
        String messageText = message.getMessage_text();
        if (messageText == null || messageText.isEmpty()) {
            return null;
        } else if (messageText.length() >= 255) {
            return null;
        }

        return messageDAO.insertMessage(message);
    }

    // Return list of all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Get message by id
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    // Delete message by id
    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    // Update message by id after conditions met
    public Message updateMessageById(int id, String messageText) {

        if (messageText == null || messageText.isEmpty()) {
            return null;
        } else if (messageText.length() >= 255) {
            return null;
        } else {
            return messageDAO.updateMessageById(id, messageText);
        }
    }

    // Get messages by account id
    public List<Message> getMessagesByAccountId(int id) {
        return messageDAO.getMessagesByAccountId(id);
    }  
}
