package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Testing umm3601.todos.TodoDatabase filterTodosByStatus and litsTodos
 * with _status_ parameters
 */
public class FilterTodosByLimit {

    @Test
    public void filterTodosByLimit() throws IOException {
    TodoDatabase tdb = new TodoDatabase("/todos.json");
    Todo[] allTodos = tdb.listTodos(new HashMap<>());

    Todo[] todoLimit10 = tdb.filterTodosByLimit(allTodos, 10);
    assertEquals(10, todoLimit10.length, "Incorrect number of todos output");

    Todo[] todoLimit5 = tdb.filterTodosByLimit(allTodos, 5);
    assertEquals(5, todoLimit5.length, "Incorrect number of todos output"); 
    }
}