package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.javalin.core.validation.Validator;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import umm3601.Server;

/**
 * Tests the logic of the todoController
 *
 * @throws IOException
 */
public class TodoControllerSpec {

  private Context ctx = mock(Context.class);

  private TodoController todoController;
  private static TodoDatabase db;

  @BeforeEach
  public void setUp() throws IOException {
    ctx.clearCookieStore();

    db = new TodoDatabase(Server.TODO_DATA_FILE);
    todoController = new TodoController(db);
  }

  @Test
  public void GET_to_request_all_todos() throws IOException {
    // Call the method on the mock controller
    todoController.getTodos(ctx);

    // Confirm that `json` was called with all the todos.
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    assertEquals(db.size(), argument.getValue().length);
  }

  @Test
  public void GET_to_request_owner_Barry_todos() throws IOException {

    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("owner", Arrays.asList(new String[] { "Barry" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` have owner name Barry.
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals("Barry", todo.owner);
    }
  }

  @Test
  public void GET_to_request_status_complete_todos() throws IOException {

    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("status", Arrays.asList(new String[] { "complete" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` are complete
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals(true, todo.status);
    }
  }

  @Test
  public void GET_to_request_status_incomplete_todos() throws IOException {

    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("status", Arrays.asList(new String[] { "incomplete" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` are complete
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals(false, todo.status);
    }
  }

  @Test
  public void GET_to_request_owner_Barry_status_complete_todos() throws IOException {

    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("owner", Arrays.asList(new String[] { "Barry" }));

    queryParams.put("status", Arrays.asList(new String[] { "complete" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` are owned by Barry
    // and are complete
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals("Barry", todo.owner);
      assertEquals(true, todo.status);
    }
  }

  @Test
  public void GET_to_request_todo_with_limit_10() throws IOException {
    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("limit", Arrays.asList(new String[] { "10" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` create an array of expected length
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals(argument.getValue().length, 10);
    }
}
//testing illegal user input
@Test
public void GET_to_request_todo_with_limit_abc() throws IOException {
  Map<String, List<String>> queryParams = new HashMap<>();
  queryParams.put("limit", Arrays.asList(new String[] { "abc" }));

  when(ctx.queryParamMap()).thenReturn(queryParams);
  todoController.getTodos(ctx);

  // Confirm that all the todos passed to `json` create an array of expected length
  ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
  verify(ctx).json(argument.capture());
  for (Todo todo : argument.getValue()) {
    assertEquals(argument.getValue().length, 300);//returns max length when met with illegal argument
  }
}
//testing limit size greater than available number of todos
@Test
public void GET_to_request_todo_with_limit_500() throws IOException {
  Map<String, List<String>> queryParams = new HashMap<>();
  queryParams.put("limit", Arrays.asList(new String[] { "500" }));

  when(ctx.queryParamMap()).thenReturn(queryParams);
  todoController.getTodos(ctx);

  // Confirm that all the todos passed to `json` create an array of expected length
  ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
  verify(ctx).json(argument.capture());
  for (Todo todo : argument.getValue()) {
    assertEquals(argument.getValue().length, 300);//returns max length when met with out of bounds size
  }
}

//testing limit size with negative limit argument (illegal, should return un-modified size)
@Test
public void GET_to_request_todo_with_limit_negative_10() throws IOException {
  Map<String, List<String>> queryParams = new HashMap<>();
  queryParams.put("limit", Arrays.asList(new String[] { "-10" }));

  when(ctx.queryParamMap()).thenReturn(queryParams);
  todoController.getTodos(ctx);

  // Confirm that all the todos passed to `json` create an array of expected length
  ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
  verify(ctx).json(argument.capture());
  for (Todo todo : argument.getValue()) {
    assertEquals(argument.getValue().length, 300);//returns max length when met with out of bounds size
  }
}
    


  @Test
  public void GET_to_request_contains_nostrud_proident() throws IOException {

    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("contains", Arrays.asList(new String[] { "nostrud proident" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` are complete
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals(true, todo.body.contains("nostrud proident"));
    }
  }
  //testing a random combination of filters
  @Test
  public void GET_to_request_owner_Barry_status_complete_contains_nisi_with_limit_rand() throws IOException {
    //generates random number between 1 and 1000 for limit testing
    double rand = (Math.random()*1000) + 1;
    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("owner", Arrays.asList(new String[] { "Barry" }));
    queryParams.put("contains", Arrays.asList(new String[] { "nisi" }));
    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    queryParams.put("limit", Arrays.asList(new String[] { "rand" }));

    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);

    // Confirm that all the todos passed to `json` are owned by Barry
    // and are complete
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo todo : argument.getValue()) {
      assertEquals("Barry", todo.owner);
      assertEquals(true, todo.status);
      assertEquals(true, todo.body.contains("nisi"));
      //array should have a length that is <= to the limit, depending on whether it has enough entries
      assertEquals(argument.getValue().length <= rand, true);
    }
  }
}