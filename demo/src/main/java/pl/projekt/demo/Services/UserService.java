package pl.projekt.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Repositories.TodoUserRepository;

import java.util.Optional;

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
    public String addNewUser(String username,String password){
        TodoUser new_user = new TodoUser(username,password);
        todoUserRepository.save(new_user);
        return "Pomyślnie dodano nowego użytkownika";
    }
}
