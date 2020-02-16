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
public class FilterTodosByStatus {

    @Test
    public void filterTodosByStatus() throws IOException {
    TodoDatabase tdb = new TodoDatabase("/todos.json");
    Todo[] allTodos = tdb.listTodos(new HashMap<>());

    Todo[] statusComplete = tdb.filterTodosByStatus(allTodos, true);
    assertEquals(143, statusComplete.length, "Incorrect number of todos with complete status");

    Todo[] statusIncomplete = tdb.filterTodosByStatus(allTodos, false);
    assertEquals(157, statusIncomplete.length, "Incorrect number of todos with incomplete status"); 
    }
}