package voting.service;

import voting.dto.CreateCandidateDto;
import voting.dto.TakeCandidateDto;
import voting.exception.AccessDenied;
import voting.model.Candidate;
import voting.model.Role;
import voting.model.User;
import voting.repository.CandidateDao;

import java.util.List;

public class CandidateServiceImpl implements CandidateService {

    private final CandidateDao candidateDao;

    public CandidateServiceImpl(CandidateDao candidateDao) {
        this.candidateDao = candidateDao;
    }


    public void addVoice(Candidate candidate) {

        Long plusVoice = candidate.getVoices();
        plusVoice = plusVoice + 1L;
        candidate.setVoices(plusVoice);
        candidateDao.update(candidate);
    }

    @Override
    public List<TakeCandidateDto> loadCandidates() {
        return CandidateDao.getInstance().load();
    }

    @Override
    public void addCandidates(CreateCandidateDto createCandidateDto) {
        User currentUser = LoginService.getCurrentUser();

        if (currentUser.getLogin().equals("adm36")) {
            var candidate = new Candidate();
            candidate.setName(createCandidateDto.getName());
            candidateDao.save(candidate);
        } else {
            throw new AccessDenied("Нет прав для совершения действия");
        }
    }
}
