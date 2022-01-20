package pl.projekt.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.projekt.demo.Classes.Todo;
import pl.projekt.demo.Classes.TodoUser;
import pl.projekt.demo.Repositories.TodoRepository;
import pl.projekt.demo.Repositories.TodoUserRepository;

import java.beans.BeanProperty;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private JdbcTemplate db;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
