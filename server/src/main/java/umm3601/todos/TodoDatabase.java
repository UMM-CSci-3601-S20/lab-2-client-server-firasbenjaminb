package umm3601.todos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.lang.Math;
import java.lang.NumberFormatException;

import com.google.gson.Gson;

/**
 * Fake database of todos info This will contain all the todos info needed for
 * the TodosController method to be able to perform its tasks.
 */
public class TodoDatabase {

  private Todo[] allTodos;

  public TodoDatabase(String todoDataFile) throws IOException {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
    allTodos = gson.fromJson(reader, Todo[].class);// gson is turning the Json into our Todo class, which is stored in
                                                   // our allTodos array
  }

  public int size() {
    return allTodos.length;
  }

  /**
   * Get an array of all todos satisfying the queries in the params
   *
   * @param queryParams
   * @return an array of all todos matching the criteria
   */
  public Todo[] listTodos(Map<String, List<String>> queryParams) {
    Todo[] filteredTodos = allTodos;

    // Filter by status if defined
    if (queryParams.containsKey("status")) {
      String targetStatus = queryParams.get("status").get(0);
      Boolean bTargetStatus;

      if (targetStatus.equals("complete")) {
        bTargetStatus = true;
      } else
        bTargetStatus = false;

      filteredTodos = filterTodosByStatus(filteredTodos, bTargetStatus);
    }
    // Filter body if defined
    if (queryParams.containsKey("contains")) {
      String targetBodyText = queryParams.get("contains").get(0);
      filteredTodos = filterTodosByBody(filteredTodos, targetBodyText);
    }
    // Filter number of todos if defined
    if (queryParams.containsKey("limit")) {
      String stringLimit = queryParams.get("limit").get(0);
      /**
       * May throw NumberFormatException
       */
      try{
      int intLimit = Integer.parseInt(stringLimit);
      filteredTodos = filterTodosByLimit(filteredTodos, intLimit);
      }catch (NumberFormatException e) {
      System.err.println("The string value cannot be parsed.");
      }
    }

    return filteredTodos;
  }

  /**
   * Get an array of all the todos with a specified string in its body
   * 
   * @param todos
   * @param targetBodyText
   * @return an array of all todos that match query params
   */
  public Todo[] filterTodosByBody(Todo[] todos, String targetBodyText) {
    return Arrays.stream(todos).filter(x -> x.body.contains(targetBodyText)).toArray(Todo[]::new);
  }

  /**
   * Get an array of all todos with target status
   *
   * @param todos        list of todos to filter by status
   * @param targetStatus desired status
   * @return an array of all todos who contain the desired status
   */
  public Todo[] filterTodosByStatus(Todo[] todos, Boolean targetStatus) {
    return Arrays.stream(todos).filter(x -> x.status == (targetStatus)).toArray(Todo[]::new);
  }

  /**
   * Get an arra of all todos capped at some user input limit
   * 
   * @param todos list of todos to cap the number displayed
   * @param intLimit  the user-specified number of todos to display
   * @return an array of all the todos from the given list that fit within the limit
   */
  public Todo[] filterTodosByLimit(Todo[] todos, int intLimit) {
    //making sure that the array stays within max bounds
    if(intLimit > 300) intLimit = 300;
    if(intLimit < -300) intLimit = 300;
    if(intLimit < 0) intLimit = Math.abs(intLimit);
    Todo[] limitedTodo = Arrays.copyOfRange(todos, 0, intLimit);
    return limitedTodo;
  }
}
