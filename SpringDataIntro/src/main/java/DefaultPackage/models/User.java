package DefaultPackage.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @Column
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Account> accounts;

    public User() {
        this.accounts = new ArrayList<>();
    }

    public User(String username, int age, Account account) {
        this();
        this.username = username;
        this.age = age;
        this.accounts.add(account);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Long> getAccountIds() {
        return this.accounts.stream().map(Account::getId)
                .collect(Collectors.toList());
    }
}
