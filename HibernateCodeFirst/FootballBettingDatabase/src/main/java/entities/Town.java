package entities;

import jakarta.persistence.*;

@Entity
@Table
public class Town extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Country country;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
