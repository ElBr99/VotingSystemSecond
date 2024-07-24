package voting.service;

import voting.dto.VotingDto;
import voting.model.Candidate;
import voting.model.Voting;

import java.util.List;

public interface VotingService {

   // String loadVotingList (VotingDto votingDto);
    void vote ( Candidate candidate);

    List<Candidate> getResultVoices ();


}
