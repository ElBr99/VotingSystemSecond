package voting.service;

import voting.dto.VotingDto;
import voting.model.Candidate;
import voting.model.User;
import voting.model.Voting;
import voting.repository.CandidateDao;
import voting.repository.VotersDAO;
import voting.repository.VotingDAO;

import java.util.List;

public class VotingServiceImpl implements VotingService {



    private final VotersDAO votersDAO;
    private final CandidateDao candidateDao=CandidateDao.getInstance();



    public VotingServiceImpl(VotersDAO votersDAO) {
        this.votersDAO = votersDAO;
    }


    @Override
    public void vote(Candidate candidate) {
        User currentUser = LoginService.getCurrentUser();
        if (!votersDAO.isExists(currentUser.getLogin())) {
            votersDAO.save(currentUser);
            CandidateServiceImpl candidateService = new CandidateServiceImpl(candidateDao);
            candidateService.addVoice(candidate);
        } else {
            throw new RuntimeException("Пользователь уже проголосовал");
        }
    }

    @Override
    public List<Candidate> getResultVoices() {
       return candidateDao.loadResults();
    }
}
