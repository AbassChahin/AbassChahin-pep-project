package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    // Service Variables
    private AccountService accountService;
    private MessageService messageService;

    // Constructor
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Post Requests
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);

        // Get Requests
        app.get("/messages", this::getMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountId);

        // Delete Requests
        app.delete("/messages/{message_id}", this::deleteMessageById);

        // Patch Requests
        app.patch("/messages/{message_id}", this::updateMessageById);


        return app;
    }

    // Endpoint function to register and insert new account
    private void register(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        } else{
            ctx.status(400);
        }
    }

    // Endpoint function to confirm login
    private void login(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if (loginAccount != null){
            ctx.json(mapper.writeValueAsString(loginAccount));
            ctx.status(200);
        } else{
            ctx.status(401);
        }
    }

    // Endpoint function to create a message
    private void createMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        // Make sure account is valid
        Account account = accountService.getAccountById(message.getPosted_by());
        if (account != null) {
            // Try to add message if account is valid
            Message addedMessage = messageService.addMessage(message);
            if (addedMessage != null){
                ctx.json(mapper.writeValueAsString(addedMessage));
                ctx.status(200);
            } else{
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    // Endpoint function to get all messages
    private void getMessages(Context ctx) throws JsonProcessingException  {
        // Get messages
        List<Message> allMessages = messageService.getAllMessages();

        // Response
        ObjectMapper mapper = new ObjectMapper();
        ctx.json(mapper.writeValueAsString(allMessages));
        ctx.status(200);
    }

    // Endpoint function to get message by id
    private void getMessageById(Context ctx) throws JsonProcessingException  {
        // Get parameter then convert to int
        String messageId = ctx.pathParam("message_id");
        int msgId = Integer.parseInt(messageId);

        // Get message
        Message message = messageService.getMessageById(msgId);

        // Response
        ObjectMapper mapper = new ObjectMapper();
        ctx.json(mapper.writeValueAsString(message));
        ctx.status(200);
    }

    // Endpoint function to delete message by id
    private void deleteMessageById(Context ctx) throws JsonProcessingException  {
        // Get parameter then convert to int
        String messageId = ctx.pathParam("message_id");
        int msgId = Integer.parseInt(messageId);

        // Get message
        Message message = messageService.deleteMessageById(msgId);

        // Response
        ObjectMapper mapper = new ObjectMapper();
        ctx.json(mapper.writeValueAsString(message));
        ctx.status(200);
    }

    // Endpoint function to update message by id
    private void updateMessageById(Context ctx) throws JsonProcessingException  {
        // Get parameters then convert to int
        String messageId = ctx.pathParam("message_id");
        int msgId = Integer.parseInt(messageId);

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        // Get message
        Message newMessage = messageService.updateMessageById(msgId, message.getMessage_text());

        // Response
        if (newMessage != null){
            ctx.json(mapper.writeValueAsString(message));
            ctx.status(200);
        } else{
            ctx.status(400);
        }
    }

    // Endpoint function to get all messages by account id
    private void getMessagesByAccountId(Context ctx) throws JsonProcessingException  {
        // Get parameter then convert to int
        String accountId = ctx.pathParam("account_id");
        int accId = Integer.parseInt(accountId);

        // Get messages
        List<Message> messages = messageService.getMessagesByAccountId(accId);

        // Response
        ObjectMapper mapper = new ObjectMapper();
        ctx.json(mapper.writeValueAsString(messages));
        ctx.status(200);
    }
}