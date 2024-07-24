package voting.service;

import voting.dto.CreateUserDto;
import voting.dto.CheckUserDto;

public interface UserService {

    void addUser(CreateUserDto userDto);

    void enter(CheckUserDto checkUserDto);

}
