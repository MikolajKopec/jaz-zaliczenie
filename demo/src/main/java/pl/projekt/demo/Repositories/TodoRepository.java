package pl.projekt.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt.demo.Classes.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    public Todo findById(long id);
    public List<Todo> findByOwnerId(long owner_id);
}
