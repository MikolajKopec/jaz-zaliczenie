package pl.projekt.demo.Controllers;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Services.TodoService;
import pl.projekt.demo.Services.UserService;

import java.util.Optional;

@Controller
public class TodoController {
    TodoService todoService;
    UserService userService;
    @Autowired
            public TodoController(TodoService todoService,UserService userService){
        this.todoService = todoService;
        this.userService = userService;
    }
    @GetMapping("/todo/{todo_id}")
    public String getTodo(@PathVariable String todo_id, ModelMap modelMap){
        Todo todo = todoService.getTodo(todo_id);
        modelMap.addAttribute("title",todo.getTitle());
        modelMap.addAttribute("description",todo.getDescription());
        return "todos/view_todos";
    }
    @RequestMapping(value="/add_new_todo",method = RequestMethod.POST)
    public String add_new_todo(@RequestParam(value="title") String title, @RequestParam(value="description") String description){
        TodoUser owner = userService.find_user_by_id("1");
        todoService.create_new_todo(title,description,owner);
        return "todos/add_new_todo";
    }
}
