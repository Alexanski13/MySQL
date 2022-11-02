package hasEntities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(targetEntity = Article.class, mappedBy = "categories")
    private Set<Article> articles;
    public Category() {
    }

    public Category(String name) {
        this();
        this.name = name;
    }
}
