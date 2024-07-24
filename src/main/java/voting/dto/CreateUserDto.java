package voting.dto;

import java.util.Objects;

public class CreateUserDto {

    private String name;
    private String login;
    private String password;

    public CreateUserDto(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserDto userDto = (CreateUserDto) o;
        return name.equals(userDto.name) && login.equals(userDto.login) && password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, password);
    }
}
