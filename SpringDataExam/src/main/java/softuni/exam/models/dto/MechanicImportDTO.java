package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MechanicImportDTO {

    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 2)
    @Column(unique = true)
    private String firstName;

    @Size(min = 2)
    @Column
    private String lastName;


    @Size(min = 2)
    @Column(unique = true)
    private String phone;
}
