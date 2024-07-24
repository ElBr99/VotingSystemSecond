package voting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import voting.model.Candidate;
import voting.model.Role;
import voting.model.User;
import voting.repository.CandidateDao;
import voting.repository.VotersDAO;

import static org.junit.jupiter.api.Assertions.*;

class VotingServiceImplTest {

    private VotersDAO votersDAO;

    private VotingServiceImpl votingService;

    private CandidateDao candidateDao;

    private CandidateServiceImpl candidateService;


    @BeforeEach
    void arrangements() {
        this.votersDAO = Mockito.mock(VotersDAO.class);
        this.votingService = new VotingServiceImpl(votersDAO);
        this.candidateDao = Mockito.mock(CandidateDao.class);
        this.candidateService = new CandidateServiceImpl(candidateDao);
    }

    @Test
    void test_vote() {

        User currentUser = new User("ol", "olga", "123", Role.USER);

        LoginService.setCurrentUser(currentUser);

        Candidate candidate = new Candidate("Vasya");

        Mockito.when(votersDAO.isExists(currentUser.getLogin())).thenReturn(false);

        votingService.vote(candidate);

        Mockito.verify(votersDAO, Mockito.times(1)).save(currentUser);

        candidateService.addVoice(candidate);


    }


}