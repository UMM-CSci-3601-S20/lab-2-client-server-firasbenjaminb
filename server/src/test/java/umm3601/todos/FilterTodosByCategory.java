package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Testing umm3601.todos.TodoDatabase filterTodosByCategory
 */
public class FilterTodosByCategory {

    @Test
    public void filterTodosByCategory() throws IOException {
    TodoDatabase tdb = new TodoDatabase("/todos.json");
    Todo[] allTodos = tdb.listTodos(new HashMap<>());

    Todo[] hwCategory = tdb.filterTodosByCategory(allTodos, "homework");
    assertEquals(79, hwCategory.length, "Incorrect number of todos with homework as category");

    Todo[] vgCategory = tdb.filterTodosByCategory(allTodos, "videogames");
    assertEquals(71, vgCategory.length, "Incorrect number of todos with video games as category"); 
    }
}