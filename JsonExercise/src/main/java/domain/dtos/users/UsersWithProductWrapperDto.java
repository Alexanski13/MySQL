package domain.dtos.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsersWithProductWrapperDto {

    private Integer usersCount;

    private List<UserWithProductsDto> users;

    public UsersWithProductWrapperDto(List<UserWithProductsDto> users) {
        this.users = users;
        this.usersCount = users.size();
    }
}
