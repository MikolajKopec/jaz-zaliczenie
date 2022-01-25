package pl.projekt.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Controllers.TodoController;
import pl.projekt.demo.Services.TodoService;
import pl.projekt.demo.Services.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;
    @MockBean
    private UserService userService;

    @Test
    public void getTodos() throws Exception, IOException {
        TodoUser todoUser = new TodoUser("test","test");
        Todo todo1 = new Todo("Zakupy","jajka, warzywa, chleb",todoUser);
        Todo todo2 = new Todo("Projekt","Dopisać jakieś testy",todoUser);
        Todo todo3 = new Todo("Ćwiczenia","",todoUser);
        List<Todo> sample_list = new ArrayList<>();
        sample_list.add(todo1);
        sample_list.add(todo2);
        sample_list.add(todo3);

        when(todoService.getTodoByOwnerId("1")).thenReturn(sample_list);

        mvc.perform(get("/todo/view_todos"))
                .andExpect(status().isOk());
    }
}
