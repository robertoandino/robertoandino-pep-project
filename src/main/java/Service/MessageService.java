package Service;

import DAO.MessageDAO;
import Model.Message;

//import java.util.Collections;
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
    public Message creatMessage(Message message){

        if(validateMessage(message)){
            return messageDAO.createNewMessage(message);
        }

        return null;
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

        if(text == ""){
            System.out.println("Message text cannot be blank");
            return false;
        }else if(text.length() > 254){
            System.out.println("Message exceeds 254 characters");
            return false;
        }else if(!messageDAO.doesUserExist(message.getPosted_by())){
            System.out.println("User not found in database");
            return false;
        }

        return true;
    }









}
