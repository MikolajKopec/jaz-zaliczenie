package pl.projekt.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.projekt.demo.Classes.CurrentUser;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Services.TodoService;
import pl.projekt.demo.Services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
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
    public String viewAllTodos(HttpSession session, ModelMap modelMap, HttpServletResponse response) throws IOException {
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
            List<Todo> todos = todoService.getTodoByOwnerId(this.currentUser.getId());
            modelMap.addAttribute("todos",todos);
        }else {
            response.sendRedirect("/user/login_user");
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","2");
        return "todos/view_todos";
    }
    @RequestMapping("/add_new_todo")
    public String add_new_todo(HttpSession session,ModelMap modelMap,HttpServletResponse response) throws IOException {
        if (this.currentUser.is_logged ==false){
            response.sendRedirect("/user/login_user");
        }
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","2");
        return "todos/add_new_todo";
    }
    @RequestMapping(value="/add_new_todo",method = RequestMethod.POST)
    public String add_new_todo(HttpSession session,ModelMap modelMap,@RequestParam(value="title") String title, @RequestParam(value="description") String description){
        Object session_current_user = session.getAttribute("current_user");
        this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        TodoUser owner = userService.find_user_by_id(currentUser.getId());
        todoService.create_new_todo(title,description,owner);
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","2");
        return "todos/add_new_todo";
    }
    @RequestMapping("/edit_todo/{todo_id}")
    public String edit_todo(@PathVariable String todo_id,HttpSession session,ModelMap modelMap,HttpServletResponse response) throws IOException {
        if (this.currentUser.is_logged ==false){
            response.sendRedirect("/user/login_user");
        }
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
            List<Todo> todos = todoService.getTodoByOwnerId(this.currentUser.getId());
            Todo todo= todoService.getTodo(todo_id);
            if (todos.contains(todo)){
            }
            else {
                response.sendRedirect("/todo/view_todos");
            }
        }
        modelMap.addAttribute("todo",todoService.getTodo(todo_id));
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","2");
        return "todos/edit_todo";
    }
    @RequestMapping(value = "/edit_todo/{todo_id}",method = RequestMethod.POST)
    public void edit_todo(@PathVariable String todo_id,@RequestParam(value="title") String title, @RequestParam(value="description") String description,HttpServletResponse response) throws IOException {
        todoService.edit_todo(todo_id,title,description);
        response.sendRedirect(String.format("/todo/edit_todo/%s",todo_id));
    }
    @RequestMapping("/delete_todo/{todo_id}")
    public void delete_todo(@PathVariable String todo_id,HttpServletResponse response) throws IOException {
        todoService.delete_todo(todo_id);
        response.sendRedirect("/todo/view_todos");
    }
}
