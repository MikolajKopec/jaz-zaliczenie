package pl.projekt.demo.Classes;

import javax.persistence.*;
import java.util.List;

@Entity(name = "todo_user")
@Table(
        name = "todo_user",
        uniqueConstraints = {
                @UniqueConstraint(name="todo_user_username_unique",columnNames = "username")
        }
)
public class TodoUser {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String username;
    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Todo> todos;

    public TodoUser( String username, String password) {
        this.username = username;
        this.password = password;
    }
    public TodoUser(){
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", todos=" + todos +
                '}';
    }
}
