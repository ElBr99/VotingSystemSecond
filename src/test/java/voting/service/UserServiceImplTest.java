package voting.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import voting.dto.CheckUserDto;
import voting.dto.CreateUserDto;
import voting.exception.IncorrectLoginOrPassword;
import voting.exception.UserAlreadyExistsException;
import voting.model.Role;
import voting.repository.UserDAO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class UserServiceImplTest {

    private UserDAO userDAO;
    private UserServiceImpl userServiceImpl;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @BeforeEach
    void arrangements() {
        this.userDAO = Mockito.mock(UserDAO.class);
        this.userServiceImpl = new UserServiceImpl(userDAO);
    }


    @Test
    void test_add_user() {
        var createUserDto = new CreateUserDto("name", "login", "password");

        Mockito.when(userDAO.isExists(eq("login")))
                .thenReturn(false);

        userServiceImpl.addUser(createUserDto);
        Mockito.verify(userDAO, Mockito.times(1))
                .save(argThat(user -> {
                    assertEquals(createUserDto.getLogin(), user.getLogin());
                    assertEquals(createUserDto.getName(), user.getName());
                    assertEquals(createUserDto.getPassword(), user.getPassword());
                    assertEquals(Role.USER, user.getRole());
                    return true;
                }));
    }

    @Test
    void throwExceptionUserAlreadyExistsException() {
        var createUserDto = new CreateUserDto("name", "login", "password");

        Mockito.when(userDAO.isExists(eq("login"))).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.addUser(createUserDto));
    }

    @Test
    void throwExceptionIncorrectLoginPassword() {
        var input = new CheckUserDto("login", "password");
        var dbResult = new CheckUserDto("login", "passwordDB");


        Mockito.when(userDAO.isExists(eq("login"))).thenReturn(true);
        Mockito.when(userDAO.getInfo(input.getLogin(), input.getPassword())).thenReturn(dbResult);

        assertThrows(IncorrectLoginOrPassword.class, () -> userServiceImpl.enter(input));
    }

    @Test
    void throwExceptionIncorrectLoginPassword2() {
        var checkUserDto = new CheckUserDto("login", "password");

        Mockito.when(userDAO.isExists(eq("login"))).thenReturn(false);
        Mockito.when(userDAO.getInfo(checkUserDto.getLogin(), checkUserDto.getPassword())).thenReturn(checkUserDto);

        assertThrows(IncorrectLoginOrPassword.class, () -> userServiceImpl.enter(checkUserDto));
    }

    @Test
    void test_enter() {
        var input = new CheckUserDto("as", "vas");
        var dbResult = new CheckUserDto("as", "vas");

        Mockito.when(userDAO.isExists(eq("as"))).thenReturn(true);
        Mockito.when(userDAO.getInfo(input.getLogin(), input.getPassword())).thenReturn(dbResult);

        userServiceImpl.enter(input);

        assertEquals("Пользователь вошёл в систему", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}