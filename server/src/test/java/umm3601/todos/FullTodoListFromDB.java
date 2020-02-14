package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests umm3601.todos.Database listTodos functionality
 */
public class FullTodoListFromDB {
    @Test
    public void totalTodoCount() throws IOException {
        Database database = new Database("/todos.json");
        Todo[] allTodos = database.listTodos(new HashMap<>());
        assertEquals(300, allTodos.length, "Incorrect total number of todos");
    }

    @Test
    public void firstTodoInFullList() throws IOException {
        Database database = new Database("/todos.json");
        Todo[] allTodos = database.listTodos(new HashMap<>());
        Todo firstTodo = allTodos[0];
        assertEquals("Blanche", firstTodo.owner, "Incorrect name" );
        assertEquals(false, firstTodo.status, "Incorrect status");
        assertEquals("In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.",
        firstTodo.body, "Incorrect body");
        assertEquals("software design", firstTodo.category, "Incorrect category");
    }
}