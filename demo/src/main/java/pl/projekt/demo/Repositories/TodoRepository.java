package pl.projekt.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt.demo.Classes.Todo;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    public Todo findById(long id);
}
