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
import pl.projekt.demo.Exceptions.UserNotFound;
import pl.projekt.demo.Services.TodoService;
import pl.projekt.demo.Services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("session")
@RequestMapping("/user")
public class UserController {
    private TodoService todoService;
    private UserService userService;
    private CurrentUser currentUser;

    @Autowired
    public UserController(TodoService todoService, UserService userService,CurrentUser currentUser) {
        this.todoService = todoService;
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @RequestMapping("/add_new_user")
    public String addNewUser(HttpSession session, ModelMap modelMap, HttpServletResponse response) throws IOException {
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","3");
        if (this.currentUser.is_logged){
            response.sendRedirect("/index");
        }
        return "user/add_new_user";
    }

    @RequestMapping(value = "/add_new_user",method = RequestMethod.POST)
    public RedirectView addNewUser(HttpSession session, ModelMap modelMap,@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="password_conf") String password_conf){
        if (password.equals(password_conf)){
            userService.addNewUser(username,password);
        }
        else {
            return new RedirectView("/user/add_new_user");
        }
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","3");
        return new RedirectView("/user/login_user");
    }

    @RequestMapping("/login_user")
    public String loginUser(HttpSession session, ModelMap modelMap,HttpServletResponse response) throws IOException {
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","3");
        if (this.currentUser.is_logged){
            response.sendRedirect("/index");
        }
        return "user/login_user";
    }
    @RequestMapping(value = "/login_user",method = RequestMethod.POST)
    public RedirectView loginUser(HttpSession session,ModelMap modelMap, @RequestParam(value="username") String username, @RequestParam(value="password") String password) throws UserNotFound {
        TodoUser user = userService.login_user(username,password);
        this.currentUser = new CurrentUser(user.getId().toString(),user.getUsername(),true);
        session.setAttribute("current_user",this.currentUser);
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        return new RedirectView("/index");
    }
    @RequestMapping(value = "/logout_user")
    public RedirectView logoutUser(HttpSession session){
        this.currentUser = new CurrentUser(null,null,false);
        session.setAttribute("current_user",this.currentUser);
        return new RedirectView("/index");
    }
    @GetMapping("/view_users")
    public String viewAllUsers(HttpSession session, ModelMap modelMap, HttpServletResponse response) throws IOException {
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user==null){
            response.sendRedirect("/user/login_user");
        }
        else if (!userService.checkIfCurrentUserIsNull(session_current_user)){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);

            List<TodoUser> users = userService.getAllUsersExceptLogged(this.currentUser.getId());
            modelMap.addAttribute("users",users);
        }else {
            response.sendRedirect("/user/login_user");
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","2");
        return "user/view_users";
    }
    @RequestMapping("/delete_user/{user_id}")
    public void delete_user(@PathVariable String user_id,HttpServletResponse response) throws IOException {
        userService.deleteUser(user_id);
        response.sendRedirect("/user/view_users");
    }
}
