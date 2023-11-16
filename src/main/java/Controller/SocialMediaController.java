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
        app.get("/accounts", this::getAllAccountsHandler);
        app.post("/accounts", this::postAccountHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/available", this::getAllAvailableMessagesHandler);
        app.post("/books", this::postMessageHandler);
        //app.start(8080);
        
        
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
        
        //check password length
        if(account.getPassword().length() < 4){
            ctx.status(400).result("Password length must be at least 4 characters");
            return;
        }
        
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllAccountsHandler(Context context) {
        List<Account> accounts = accountService.getAllAccounts();
        context.json(accounts);
    }

    /**
     * Handler to retrieve all messages
     * @param ctx
     */
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
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
     * Handler get all available messages that != NULL
     * @param context
     */
    private void getAllAvailableMessagesHandler(Context context){
        context.json(messageService.getAllAvailableMessages());
    }



}