package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    // Insert a new message into the message table
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // set username and password strings then execute
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            // If successful return the new message
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = pkeyResultSet.getInt("message_id");
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Get all messages
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();

        // List containing all messages
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Get message by id
    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set id of message
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Delete message by id
    public Message deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        // Check if exists
        Message message = getMessageById(id);
        if (message != null) {
            try {
                String sql = "DELETE FROM message WHERE message_id = ?;" ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                // Set id of message
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        } else {
            return null;
        }
        return message;
    }

    // Update message by id
    public Message updateMessageById(int id, String messageText) {
        Connection connection = ConnectionUtil.getConnection();

        // Check if exists
        Message message = getMessageById(id);
        if (message != null) {
            try {
                String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                // Set id and text of message
                preparedStatement.setString(1, messageText);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                message = getMessageById(id);
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        } else {
            return null;
        }
        return message;
    }

    // Get messages by account id
    public List<Message> getMessagesByAccountId(int id) {
        Connection connection = ConnectionUtil.getConnection();

        // List containing all messages
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set account id and execute
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
