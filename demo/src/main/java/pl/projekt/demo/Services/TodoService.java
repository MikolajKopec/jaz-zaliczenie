package pl.projekt.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Repositories.TodoRepository;

import java.util.List;

@Component
public class TodoService {
    TodoRepository todoRepository;
    @Autowired
    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }
    public List<Todo> getTodoByOwnerId(String owner_id){
        return todoRepository.findByOwnerId(Long.parseLong(owner_id));
    }
    public Todo getTodo(String todo_id){
        return todoRepository.findById(Long.parseLong(todo_id));
    }
    public String create_new_todo(String title, String description, TodoUser owner){
        Todo new_todo = new Todo(title,description,owner);
        todoRepository.save(new_todo);
        return "Nowe zadanie zostało pomyślnie dodane";
    }
    public String edit_todo(String todo_id,String title,String description){
        Todo todo = todoRepository.findById(Long.parseLong(todo_id));
        todo.setTitle(title);
        todo.setDescription(description);
        todoRepository.save(todo);
        return "Zadanie pomyślnie edytowane";
    }
    public String delete_todo(String todo_id){
        Todo todo = todoRepository.findById(Long.parseLong(todo_id));
        todoRepository.delete(todo);
        return "Zadanie pomyślnie usunięte";
    }
    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }
}
