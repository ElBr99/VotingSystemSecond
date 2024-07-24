package voting.repository;

import voting.dto.CheckUserDto;
import voting.model.Role;
import voting.model.User;
import voting.utils.ConnectionManager;
import java.sql.SQLException;

public class UserDAO {

    private static final UserDAO INSTANCE = new UserDAO();

    private UserDAO() {

    }

    private static final String EXISTS_SQL = "" +
            "select login is null\n" +
            "  from voting_system_repo.users u \n" +
            "  where login = ?";


    private static final String SAVE_SQL = " insert into voting_system_repo.users (login, name, password, role) values (?, ?, ?, ?) ";

    private static final String CHECK_SQL = "select login, password from voting_system_repo.users where login = ? and password = ? ";

    public static UserDAO getInstance() {
        return INSTANCE;
    }


    public boolean isExists(String login) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(EXISTS_SQL)) {
            preparedStatement.setString(1, login);
            var resultSet = preparedStatement.executeQuery();
            boolean set = false;
            if (resultSet.next()) {
                //set = resultSet.getBoolean(1);
                set=true;
            }
            return set;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, String.valueOf(Role.USER));
            var resultSet = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CheckUserDto getInfo(String login,  String password ) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(CHECK_SQL)) {
            preparedStatement.setString(1,login);
            preparedStatement.setString(2, password);
//           preparedStatement.setString(3, String.valueOf(role));

            var resulSet = preparedStatement.executeQuery();
            String newLogin = " ";
            String newPassword=" ";
//            String newRole = null;
            if (resulSet.next()) {
            newLogin = resulSet.getString(1);
            newPassword = resulSet.getString(2);
//            newRole = resulSet.getString(3);
            }
            CheckUserDto checkUserDto = new CheckUserDto(newLogin, newPassword);
            return checkUserDto;

        } catch (SQLException e) {
            e.printStackTrace();
           throw new RuntimeException();

        }

    }


}


