package Service;

import DAO.MessageDAO;
import Model.Message;

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

    public Message addMessage(Message message) {
        String messageText = message.getMessage_text();
        if (messageText == null || messageText.isEmpty()) {
            return null;
        } else if (messageText.length() >= 255) {
            return null;
        } else if ()
    }
}
