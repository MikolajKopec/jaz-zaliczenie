package pl.projekt.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.projekt.demo.Classes.CurrentUser;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Exceptions.UserNotFound;
import pl.projekt.demo.Repositories.TodoUserRepository;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class UserService {
    TodoUserRepository todoUserRepository;

    @Autowired
    public UserService(TodoUserRepository todoUserRepository){
        this.todoUserRepository = todoUserRepository;
    }

    public TodoUser find_user_by_id(String user_id){
        return todoUserRepository.findById(Long.parseLong(user_id));
    }
    public TodoUser find_user_by_username(String username){ return todoUserRepository.findByUsername(username);}
    public List<TodoUser> getAllUsersExceptLogged(String logged_id){
        TodoUser logged_user = find_user_by_id(logged_id);
        List<TodoUser> list_of_users = this.todoUserRepository.findAll();
        list_of_users.remove(logged_user);
        return list_of_users;
    }
    public TodoUser login_user(String username,String password) throws UserNotFound {
        TodoUser user = find_user_by_username(username);
        if (user==null){
            throw new UserNotFound();
        }
         if (user.getPassword().equals(password)){
            return user;
        }
         else {
             throw new UserNotFound();
         }
    }
    public String addNewUser(String username,String password){
        TodoUser new_user = new TodoUser(username,password);
        todoUserRepository.save(new_user);
        return "Pomyślnie dodano nowego użytkownika";
    }
    public void deleteUser(String user_id){
        TodoUser user = todoUserRepository.findById(Long.parseLong(user_id));
        todoUserRepository.delete(user);
    }
    public CurrentUser setCurrentUserFromSession(Object session_current_user){
        Object someObject = session_current_user;
        CurrentUser currentUser = new CurrentUser();
        for (Field field : someObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(someObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                if (field.getName().equals("id"))
                {
                    currentUser.setId(value.toString());
                }
                else if (field.getName().equals("username")){
                    currentUser.setUsername(value.toString());
                }
                else if(field.getName().equals("is_logged")){
                    currentUser.setIs_logged(Boolean.parseBoolean(value.toString()));
                }
            }
        }
        return currentUser;
    }
    public Boolean checkIfCurrentUserIsNull(Object session_current_user){
        Object someObject = session_current_user;
        for (Field field : someObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(someObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null) {
                return true;
            }
            else {
                return false;
            }
        }
        return null;
    }
}