package umm3601.todos;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

/**
 * This is the controller that manages requests about todos
 */
public class TodoController{

    private Database database;
    /**
     * Creating controller for todos
     * This loads the database of todo info from the json file and stores it internaly
     * so we can return user specified subsets of todos.
     * @param database our database of todo info
     */
    public TodoController(Database database) {
        this.database = database;
    }

    /**
     * Get a JSON response will a list of all the todos in our database
     * 
     * @param ctx 
     */
    public void getTodos(Context ctx) {
        Todo[] todos = database.listTodos(ctx.queryParamMap());
        ctx.json(todos);
    }
}