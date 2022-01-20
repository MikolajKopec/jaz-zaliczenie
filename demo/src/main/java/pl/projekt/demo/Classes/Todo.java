package pl.projekt.demo.Classes;

import javax.persistence.*;

@Entity(name = "todo")
public class Todo {
    @Id
    @SequenceGenerator(
            name = "todo_sequence",
            sequenceName = "todo_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "todo_sequence"
    )
    private Long id;
    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="owner_id",nullable = false)
    private TodoUser owner;

    public Todo( String title, String description,TodoUser owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    public Todo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TodoUser getOwner() {
        return owner;
    }

    public void setOwner(TodoUser owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", owner_id=" + owner +
                '}';
    }
}
