package voting.service;

import voting.dto.TakeCandidateDto;
import voting.model.Candidate;
import voting.dto.CreateCandidateDto;

import java.util.List;

public interface CandidateService {

    void addVoice (Candidate candidate);

  //  Long getVoices (Candidate candidate);

    List <TakeCandidateDto> loadCandidates();

    void addCandidates (CreateCandidateDto createCandidateDto);


}
