package voting.service;

import voting.exception.IncorrectLoginOrPassword;
import voting.exception.UserAlreadyExistsException;
import voting.model.User;
import voting.repository.UserDAO;
import voting.dto.CheckUserDto;
import voting.dto.CreateUserDto;
import voting.model.Role;

import java.util.Objects;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(CreateUserDto userDto) {
        if (userDAO.isExists(userDto.getLogin())) {
            throw new UserAlreadyExistsException("Пользователь уже зарегистрирован");
        }

        var user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setRole(Role.USER);

        userDAO.save(user);
    }

    @Override
    public void enter(CheckUserDto checkUserDto) {

        CheckUserDto resultGetInfo = userDAO.getInfo(checkUserDto.getLogin(), checkUserDto.getPassword());

        User user = new User();
        user.setLogin(checkUserDto.getLogin());
        user.setPassword(checkUserDto.getPassword());


        if (!userDAO.isExists(checkUserDto.getLogin()) || !resultGetInfo.getPassword().equals(checkUserDto.getPassword())) {
            throw new IncorrectLoginOrPassword("Неверный логин и(или) пароль");
        } else {
            System.out.println("Пользователь вошёл в систему");
            LoginService.setCurrentUser(user);
        }

//        if (resultGetInfo.equals(checkUserDto)) {
//            System.out.println("Пользователь вошёл в систему");
//            LoginService.setCurrentUser(user);
//        } else  if (resultGetInfo.getLogin().equals(checkUserDto.getLogin()) && !Objects.equals(resultGetInfo.getPassword(), checkUserDto.getPassword())) {
//            System.out.println("Указан неверный пароль");
//        } else if (!Objects.equals(resultGetInfo.getLogin(), checkUserDto.getLogin()) && resultGetInfo.getPassword().equals(checkUserDto.getPassword())) {
//            System.out.println("Указан неверный логин");
//        } else  {
//            throw new UserNotRegisteredException("Пользователь еще не зарегистрирован в системе");
//        }
    }

}
