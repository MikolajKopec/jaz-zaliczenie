package pl.projekt.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.projekt.demo.Classes.CurrentUser;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Services.TodoService;
import pl.projekt.demo.Services.UserService;

import javax.servlet.http.HttpSession;

@Controller
@Scope("session")
@RequestMapping("/user")
public class UserController {
    TodoService todoService;
    UserService userService;
    CurrentUser currentUser;
    @Autowired
    public UserController(TodoService todoService, UserService userService,CurrentUser currentUser) {
        this.todoService = todoService;
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @RequestMapping("/add_new_user")
    public String addNewUser(){
        return "user/add_new_user";
    }

    @RequestMapping(value = "/add_new_user",method = RequestMethod.POST)
    public String addNewUser(@RequestParam(value="username") String username, @RequestParam(value="password") String password,@RequestParam(value="password_conf") String password_conf){

        if (password.equals(password_conf)){
            userService.addNewUser(username,password);
        }
        return "user/add_new_user";
    }
    @RequestMapping("/login_user")
    public String loginUser(){
        return "user/login_user";
    }
    @RequestMapping(value = "/login_user",method = RequestMethod.POST)
    public RedirectView loginUser(HttpSession session, @RequestParam(value="username") String username, @RequestParam(value="password") String password){
        TodoUser user = userService.login_user(username,password);
        this.currentUser = new CurrentUser(user.getId().toString(),user.getUsername());
        session.setAttribute("current_user",this.currentUser);
        return new RedirectView("/index");
    }
}
