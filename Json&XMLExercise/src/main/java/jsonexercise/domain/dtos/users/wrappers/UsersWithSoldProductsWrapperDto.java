package jsonexercise.domain.dtos.users.wrappers;

import jsonexercise.domain.dtos.users.UserWithSoldProductsXmlDto;
import jsonexercise.domain.dtos.users.UsersWithSoldProductsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsWrapperDto {

    @XmlElement(name = "user")
    private List<UserWithSoldProductsXmlDto> users;

    public UsersWithSoldProductsWrapperDto ofListOfUsersWithSoldProductsDto(List<UsersWithSoldProductsDto> input) {
        users = UsersWithSoldProductsDto.toUsersWithSoldProductsDto(input);

        return this;
    }
}
