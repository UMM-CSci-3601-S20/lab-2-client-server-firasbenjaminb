package umm3601.todos;
import java.util.Comparator;

public class Todo{

    public String _id;
    public String owner;
    public boolean status;
    public String body;
    public String category;

}
/*
class SortByOwner implements Comparator<Todo>{
    public int compare(Todo a, Todo b){
        return a.owner.compareTo(b.owner);
    }
}
class SortByStatus implements Comparator<Todo>{
    public int compare(Todo a, Todo b){
        return (Boolean) a.status.compareTo( (Boolean) b.status);
    }
}

class SortByBody implements Comparator<Todo>{
    public int compare(Todo a, Todo b){
        return a.body.compareTo(b.body);
    }
}
class SortByCategory implements Comparator<Todo>{
    public int compare(Todo a, Todo b){
        return a.category.compareTo(b.category);
    }
}
*/
