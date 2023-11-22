package DAO;

import Util.ConnectionUtil;
import Model.Message;

//import static org.mockito.Mockito.ignoreStubs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Retrieve all messages 
     * @return all messages
     */
    public List<Message> getAllMessages(){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{

            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch")
                );
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
    public Message getMessageById(int id){

        Connection connection = ConnectionUtil.getConnection();
        try{

            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            e.printStackTrace();
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

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
        
            int rowsAffected = preparedStatement.executeUpdate();


            if(rowsAffected > 0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedMessageId = generatedKeys.getInt(1);
                    message.setMessage_id(generatedMessageId);
    
                }
            }
            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Deletes message
     * @param message
     * @return message if deleted
     */
    public Message deleteMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            
            String sql = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getMessage_id());

            preparedStatement.executeUpdate();

            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Checks if a user exists
     * @param userId
     * @return true or false
     */
    public boolean doesUserExist(int userId){
        Connection connection = ConnectionUtil.getConnection();
        try{
            
            String sql = "SELECT COUNT(*) FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareCall(sql);

            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();
            return true;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    
}
