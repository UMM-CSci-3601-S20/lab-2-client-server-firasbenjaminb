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
public class FilterTodosByBody {

    @Test
    public void filterTodosByBody() throws IOException {
    TodoDatabase tdb = new TodoDatabase("/todos.json");
    Todo[] allTodos = tdb.listTodos(new HashMap<>());
    //note: nostrud proident actually occurs twice, but one instance has a capitalized n, which contains treats as a different char sequence
    Todo[] temporBody = tdb.filterTodosByBody(allTodos, "nostrud proident");
    assertEquals(1, temporBody.length, "Incorrect number of todos with nostrud proident in body");

    Todo[] statusIncomplete = tdb.filterTodosByBody(allTodos, "in cillum");
    assertEquals(5, statusIncomplete.length, "Incorrect number of todos with in cillum in body"); 
    }
}