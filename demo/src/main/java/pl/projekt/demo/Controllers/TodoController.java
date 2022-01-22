package pl.projekt.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.projekt.demo.Classes.CurrentUser;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Services.TodoService;
import pl.projekt.demo.Services.UserService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.List;


@Controller
@Scope("session")
@RequestMapping("/todo")
public class TodoController {
    TodoService todoService;
    UserService userService;
    CurrentUser currentUser;
    @Autowired
            public TodoController(TodoService todoService,UserService userService,CurrentUser currentUser){
        this.todoService = todoService;
        this.userService = userService;
        this.currentUser = currentUser;
    }
    @GetMapping("/view_todos")
    public String viewAllTodos(ModelMap modelMap){
        List<Todo> todos = todoService.getAllTodos();
        modelMap.addAttribute("todos",todos);
        return "todos/view_todos";
    }
    @RequestMapping("/add_new_todo")
    public String add_new_todo(){
        return "todos/add_new_todo";
    }
    @RequestMapping(value="/add_new_todo",method = RequestMethod.POST)
    public String add_new_todo(HttpSession session,@RequestParam(value="title") String title, @RequestParam(value="description") String description){
        Object session_current_user = session.getAttribute("current_user");
        this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        TodoUser owner = userService.find_user_by_id(currentUser.getId());
        todoService.create_new_todo(title,description,owner);
        return "todos/add_new_todo";
    }
}
