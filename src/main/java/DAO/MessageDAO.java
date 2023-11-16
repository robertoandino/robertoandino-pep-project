package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Retrieve all messages 
     * @return all messages
     */
    public List<Message> getAllMessages(){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList();
        try{

            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

     /**
     * Retrieve all available messages 
     * @return all available messages
     */
    public List<Message> getAllAvailableMessages(){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList();
        try{

            String sql = "SELECT * FROM Message WHERE message_id IS NOT NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Retrieve all available messages by ID
     * @return all available messages by id
     */
    public Message getMessagesById(int id){

        Connection connection = ConnectionUtil.getConnection();
        //List<Message> messages = new ArrayList();
        try{

            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                //messages.add(message);
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Create new message
     * @param message
     * @return new created message
     */
    public Message createNewMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "INSERT INTO Message (message_id, posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareCall(sql);

            preparedStatement.setInt(1, message.getMessage_id());
            preparedStatement.setInt(2, message.getPosted_by());
            preparedStatement.setString(3, message.getMessage_text());
            preparedStatement.setLong(4, message.getTime_posted_epoch());
        
            preparedStatement.executeUpdate();
            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
