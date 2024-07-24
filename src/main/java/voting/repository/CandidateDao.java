package voting.repository;

import voting.dto.CreateCandidateDto;
import voting.dto.TakeCandidateDto;
import voting.model.Candidate;
import voting.utils.ConnectionManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidateDao {

    private static final CandidateDao INSTANCE = new CandidateDao();

    public CandidateDao() {
    }

    public static CandidateDao getInstance() {
        return INSTANCE;
    }


    String SAVE_SQL = " insert into voting_system_repo.candidates (name) values (?) ";


    String LOAD_SQL = "select name from voting_system_repo.candidates";

    String UPDATE_VOICES = "update voting_system_repo.candidates set voices = ? where name = ?";

    String LOAD_RESULTS = "select name, voices from voting_system_repo.candidates";

    public void update (Candidate candidate) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_VOICES)) {
            preparedStatement.setLong(1, candidate.getVoices());
            preparedStatement.setString(2, candidate.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


    }

    public void save(Candidate candidate) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, candidate.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<TakeCandidateDto> load() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(LOAD_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<TakeCandidateDto> candidateList = new ArrayList<>();
            while (resultSet.next()) {
                TakeCandidateDto takeCandidateDto = new TakeCandidateDto(resultSet.getString("name"));
                candidateList.add(takeCandidateDto);
            }
            return candidateList;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Candidate> loadResults() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(LOAD_RESULTS)) {
            var resultSet = preparedStatement.executeQuery();
            List<Candidate> candidateList = new ArrayList<>();
            while (resultSet.next()) {
                Candidate candidate = new Candidate(resultSet.getString("name"), resultSet.getLong("voices"));
                candidateList.add(candidate);
            }
            return candidateList;

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


}
