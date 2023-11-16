package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    
    public MessageDAO messageDAO;

    /**
     * Constructor to create a MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for mock behavior of BookDAO
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

    public Message creatMessage(Message message){

        if(messageDAO.getMessagesById(message.getMessage_id()) != null){

            return null;
        }

        Message persistedMessage = messageDAO.createNewMessage(message);

        return persistedMessage;
    }

    public List<Message> getAllAvailableMessages(){

        List<Message> messages = messageDAO.getAllAvailableMessages();

        return messages;
    }






}
