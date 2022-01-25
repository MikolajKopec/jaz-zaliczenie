package pl.projekt.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt.demo.Classes.TodoUser;

import java.util.List;

public interface TodoUserRepository extends JpaRepository<TodoUser,Long> {
    public TodoUser findById(long id);
    public TodoUser findByUsername(String username);
}
