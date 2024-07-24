package voting.repository;

import voting.model.User;
import voting.utils.ConnectionManager;

import java.sql.SQLException;

public class VotersDAO {

    private static final VotersDAO INSTANCE = new VotersDAO();

    public VotersDAO() {
    }

    public static VotersDAO getInstance() {
        return INSTANCE;
    }


    private static final String EXISTS_SQL = "select user_login is null from voting_system_repo.voted where user_login = ? ";


    private static final String SAVE_SQL = " insert into voting_system_repo.voted " +
            "(user_login) values (?) ";


    public boolean isExists(String login) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(EXISTS_SQL)) {
            preparedStatement.setString(1, login);
            var resultSet = preparedStatement.executeQuery();
            boolean set = false;
            if (resultSet.next()) {
                set = resultSet.getBoolean(1);
                set = true;
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
            var resultSet = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
