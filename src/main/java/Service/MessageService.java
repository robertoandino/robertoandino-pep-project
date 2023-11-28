package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    
    public MessageDAO messageDAO;
    public Message messageTest;

    /**
     * Constructor to create a MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for mock behavior of messageDAO
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

     /**
     * Retrieve all messages
     * @return all messages
     */
    public List<Message> getAllMessages(){

        List<Message> messages = messageDAO.getAllMessages();

        return messages;

    }

    /**
     * Gets all messages posted by given user 
     * @param accountId
     * @return
     */
    public List<Message> getAllMessagesFromUser(int accountId){
        
        return messageDAO.getMessagesByUser(accountId);
    }

    /**
     * Retrieve message by ID
     * @param id
     * @return message retrieved
     */
    public Message getMessageById(int id){

        return messageDAO.getMessageById(id);
    }

    /**
     * Create new message
     * @param message
     * @return created message
     */
    public Message createMessage(Message message){

        if(validateMessage(message) != false){
            return messageDAO.createNewMessage(message);
        }

        return null;
    }

    /*
     * Updates message if conditions are met
     */
    public Message updateMessage(int messageId, String newText){

        if(messageDAO.getMessageById(messageId) == null){
            System.out.println("Message does not exist");
            return null;
        }else if(newText.isEmpty()){
            System.out.println("Message text cannot be blank");
            return null;
        }else if(newText.length() > 254){
            System.out.println("Message exceeds 254 characters");
            return null;
        }
        
        return messageDAO.updateMessageText(messageId, newText);
        
    }

    /*
     * Delete message
     */
    public Message deleteMessage(Message message){

        return messageDAO.deleteMessage(message);
    }

    /**
     * Validates message
     * @param message
     * @return false or true
     */
    private boolean validateMessage(Message message){

        String text = message.getMessage_text();

        
        if(text.isEmpty()){
            System.out.println("Message text cannot be blank");
            return false;
        }
        
        if(text.length() > 254){
            System.out.println("Message exceeds 254 characters");
            return false;
        }
        
        if(!messageDAO.doesUserExist(message.getPosted_by())){
            System.out.println("User not found in database");
            return false;
        }
        

        return true;
    }









}
