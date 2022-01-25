package pl.projekt.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projekt.demo.Classes.CurrentUser;
import pl.projekt.demo.Services.UserService;

import javax.servlet.http.HttpSession;

@Controller
@Scope("session")
public class ErrorControler implements ErrorController {
    CurrentUser currentUser;
    UserService userService;

    @Autowired
    public ErrorControler(CurrentUser currentUser, UserService userService) {
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @RequestMapping("/error")
    public String error(HttpSession session, ModelMap modelMap){
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","1");
        return "errors/error_main.html";
    }
}
