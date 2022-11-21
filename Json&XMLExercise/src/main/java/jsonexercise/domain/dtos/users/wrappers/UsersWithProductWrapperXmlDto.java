package jsonexercise.domain.dtos.users.wrappers;

import jsonexercise.domain.dtos.users.UserWithProductsDto;
import jsonexercise.domain.dtos.users.UserWithProductsXmlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsersWithProductWrapperXmlDto {

    private Integer usersCount;

    private List<UserWithProductsDto> users;

//    public UsersWithProductWrapperJsonDto(List<UserWithProductsDto> users) {
//        this.users = users;
//        this.usersCount = users.size();
//    }
}
