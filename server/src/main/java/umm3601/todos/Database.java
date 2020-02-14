package umm3601.todos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Fake database of todos info
 * This will contain all the todos info needed for the TodosController method to be able to perform its tasks.
 */
public class Database{

    private Todo[] allTodos;

    public Database(String todoDataFile) throws IOException {
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
        allTodos = gson.fromJson(reader, Todo[].class);//gson is turning the Json into our Todo class, which is storedin our allTodos array
    }

    public int size() {
        return allTodos.length;
    }

    public Todo[] listTodos(Map<String, List<String>> queryParams) {
        Todo[] filteredTodos = allTodos;
        return filteredTodos;
    }
}