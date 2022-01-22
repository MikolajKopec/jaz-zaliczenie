package pl.projekt.demo.Classes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class CurrentUser {
    public String id;
    public String username;

    public CurrentUser(){}
    public CurrentUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "CurrentUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
