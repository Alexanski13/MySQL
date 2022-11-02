package entities;

import jakarta.persistence.*;

@Entity
@Table
public class Color extends BaseEntity{

    @Column
    private String name;
}
