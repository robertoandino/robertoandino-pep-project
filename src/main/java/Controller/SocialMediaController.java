package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/accounts", this::postAccountHandler);
        app.get("accounts/{accountId}/messages", this::getAllMessagesFromUserHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{id}", this::getMessageByIdHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{messageId}", this::deleteMessageHandler);
        app.patch("/messages/{id}", this::updateMessageTextHandler);
        
        return app;
    }

    /**
     * Handler to post new account
     * @param ctx Handles HTTP reqests and generates responses within Javalin.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object. 
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        //check password length
        if(account.getPassword().length() < 4){
            ctx.status(400).result("Password length must be at least 4 characters");
            return;
        }
        
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages
     * @param ctx
     */
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getAllMessagesFromUserHandler(Context ctx){
        
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));

        List<Message> messages = messageService.getAllMessagesFromUser(accountId);
        
        ctx.json(messages);
    }

    /**
     * Handler to create new message
     * @param ctx handles HTTP requests
     * @throws JsonProcessingException will be thrown if there is an issue
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.creatMessage(message);

        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to get message by id
     * @param ctx
     */
    public void getMessageByIdHandler(Context ctx){
        
        String messageId = ctx.pathParam("id");
        int message_id = Integer.parseInt(messageId);
        Message message = messageService.getMessageById(message_id);

        if(message == null){

            ctx.status(200).result("");

        }else{
    
            ctx.json(message);
        }
        

    }

    /**
     * Handler to delete message
     * @param ctx
     */
    public void deleteMessageHandler(Context ctx){

        int messageId = Integer.parseInt(ctx.pathParam("messageId"));
        Message message = messageService.getMessageById(messageId);

        if(message == null)
        {
            ctx.status(200).result("");
        
        }else{
            ctx.json(message);
        }
    }

    /**
     * Handler to update message
     * @param ctx
     */
    public void updateMessageTextHandler(Context ctx) {
         
         int messageId = Integer.parseInt(ctx.pathParam("id"));
         String newMessageText = ctx.bodyAsClass(Message.class).getMessage_text();

         Message message = messageService.updateMessage(messageId, newMessageText);

         
        if (message != null) {
             
             Message updatedMessage = messageService.getMessageById(messageId);
             ctx.status(200).json(updatedMessage);
         } else {
            ctx.status(400).result("");
        }
    
    }

}