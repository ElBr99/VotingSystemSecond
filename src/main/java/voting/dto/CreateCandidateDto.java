package voting.dto;

import java.util.Objects;

public class CreateCandidateDto {

    private String name;
    private static Long voices = 0L;

    public CreateCandidateDto(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCandidateDto that = (CreateCandidateDto) o;
        return name.equals(that.name) && voices.equals(that.voices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voices);
    }
}
