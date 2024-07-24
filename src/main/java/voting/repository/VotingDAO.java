package voting.repository;

import voting.model.Voting;
import voting.utils.ConnectionManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VotingDAO {

    private static final VotingDAO INSTANCE = new VotingDAO();

    private VotingDAO() {

    }

    public static VotingDAO getInstance() {
        return INSTANCE;
    }

    private static final String LOAD_VOTING_SQL = "select id, name from voting_system_repo.elections";


    public List<Voting> load() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(LOAD_VOTING_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Voting> votingList = new ArrayList<>();
            while (resultSet.next()) {
                Voting voting = new Voting(resultSet.getInt(1), resultSet.getString(2));
                votingList.add(voting);
            }
            return votingList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
