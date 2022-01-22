package pl.projekt.demo.Classes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class CurrentUser {
    public String id;
    public String username;
    public boolean is_logged;
    public CurrentUser(){}
    public CurrentUser(String id, String username, boolean is_logged) {
        this.id = id;
        this.username = username;
        this.is_logged = is_logged;
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

    public boolean isIs_logged() {
        return is_logged;
    }

    public void setIs_logged(boolean is_logged) {
        this.is_logged = is_logged;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", is_logged=" + is_logged +
                '}';
    }
}
