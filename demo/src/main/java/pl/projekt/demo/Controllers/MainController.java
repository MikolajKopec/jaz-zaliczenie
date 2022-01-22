package pl.projekt.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.projekt.demo.Classes.CurrentUser;
import pl.projekt.demo.Services.UserService;

import javax.servlet.http.HttpSession;

@Controller
@Scope("session")
public class MainController {
    CurrentUser currentUser;
    UserService userService;
    @Autowired
    public MainController(CurrentUser currentUser, UserService userService) {
        this.currentUser = currentUser;
        this.userService = userService;
    }




    @RequestMapping({"/index","/"})
    public String index(HttpSession session, ModelMap modelMap){
        Object session_current_user = session.getAttribute("current_user");
        if (session_current_user!=null){
            this.currentUser = userService.setCurrentUserFromSession(session_current_user);
        }
        modelMap.addAttribute("current_user",this.currentUser);
        modelMap.addAttribute("current_page","1");
        return "base";
    }
}
