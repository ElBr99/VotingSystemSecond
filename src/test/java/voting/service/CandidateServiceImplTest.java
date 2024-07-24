package voting.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import voting.dto.CreateCandidateDto;
import voting.exception.AccessDenied;
import voting.model.Candidate;
import voting.model.Role;
import voting.model.User;
import voting.repository.CandidateDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;

class CandidateServiceImplTest {

    private CandidateDao candidateDao;

    private CandidateServiceImpl candidateService;

    @Test
     void test_add_voice () {
        this.candidateDao = Mockito.mock(CandidateDao.class);
        this.candidateService = new CandidateServiceImpl(candidateDao);

        var candidate = new Candidate("IVan", 1L);

        candidateService.addVoice(candidate);

        Mockito.verify(candidateDao, Mockito.times(1))
                .update(argThat(candidate1 -> {
                    assertEquals(2, candidate1.getVoices());
                    assertEquals(candidate.getName(), candidate1.getName());
                    return true;
                }));

    }


     @Test

    void test_add_candidate () {
        this.candidateDao = Mockito.mock(CandidateDao.class);
        this.candidateService = new CandidateServiceImpl(candidateDao);

        var user = new User("adm", "adm36", "adm", Role.ADMIN);

        LoginService.setCurrentUser(user);

        var candidate = new Candidate("IVAN");

        var createCandidateDto = new CreateCandidateDto("IVAN");

        candidateService.addCandidates(createCandidateDto);

        Mockito.verify(candidateDao, Mockito.times(1)).save(candidate);

    }


    @Test

    void throwExceptionAccessDenied () {
        this.candidateDao = Mockito.mock(CandidateDao.class);
        this.candidateService = new CandidateServiceImpl(candidateDao);

        var user = new User ("ada", "ada", "ada", Role.USER);

        LoginService.setCurrentUser(user);

        var candidate = new Candidate("Ivan");

        var createCandidateDto = new CreateCandidateDto("Ivan");

        assertThrows(AccessDenied.class, ()->candidateService.addCandidates(createCandidateDto));


    }


}