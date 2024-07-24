package voting.dto;

import voting.model.Candidate;

import java.util.Objects;

public class TakeCandidateDto {
    private String name;
    private Long voices=0L;

    @Override
    public String toString() {
        return  name;
    }

    public TakeCandidateDto(String name) {
        this.name = name;
    }

    public TakeCandidateDto (String name, Long voices) {
        this.name=name;
        this.voices=voices;
    }


    public TakeCandidateDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoices(Long voices) {
        this.voices = voices;
    }

    public Long getVoices() {
        return voices;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
