package umm3601.todos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    // Filter by owner if defined
    if (queryParams.containsKey("owner")) {
      String targetOwner = queryParams.get("owner").get(0);
      filteredTodos = filterTodosByOwner(filteredTodos, targetOwner);
    }
    // Filter by category if defined
    if(queryParams.containsKey("category")) {
      String targetCategory = queryParams.get("category").get(0);
      filteredTodos = filterTodosByCategory(filteredTodos, targetCategory);
    }
    
    // Order by specified field
    /*
    if(queryParams.containsKey("orderBy")) {
      String targetOrder = queryParams.get("orderBy").get(0);
      filteredTodos = orderTodosByParameter(filteredTodos, targetOrder);
    }*/

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
   * Get an array of all todos with target owner
   * @param todos
   * @param targetOwner
   * @return an array of all todos with the desired owner
   */
  public Todo[] filterTodosByOwner(Todo[] todos, String targetOwner) {
    return Arrays.stream(todos).filter(x -> x.owner.equalsIgnoreCase((targetOwner.replaceAll(" ", "")))).toArray(Todo[]::new);
  }
  //sorts the todos by the target parameter alphabetically
  /*
  public Todo[] orderTodosByParameter(Todo[] todos, String targetOrder) {
   if (targetOrder.equals("owner")){
     Collections.sort(todos, new SortByOwner());
   }
   else if (targetOrder.equals("category")){
     Collections.sort(todos, new SortByCategory());
   }
   else if (targetOrder.equals("body")){
     Collections.sort(todos, new SortByBody());
   }
   /*else if (targetOrder.equals("status")){
     Collections.sort(todos, new SortByStatus());
   }
    return todos;
  }*/

  /**
   * Get an array of all todos with target category
   * @param todos list of all todos to be filtered by category
   * @param targetCategory desired category
   * @return an array of all todos with the desired category
   */
  public Todo[] filterTodosByCategory(Todo[] todos, String targetCategory) {
    return Arrays.stream(todos).filter(x -> x.category.replaceAll(" ", "").equalsIgnoreCase((targetCategory.replaceAll(" ", "")))).toArray(Todo[]::new);
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
    if(intLimit > todos.length) intLimit = todos.length;
    if(intLimit < 0) intLimit = todos.length;
    Todo[] limitedTodo = Arrays.copyOfRange(todos, 0, intLimit);
    return limitedTodo;
  }
}
