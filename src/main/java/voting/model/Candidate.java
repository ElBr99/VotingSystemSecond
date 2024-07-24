package voting.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Candidate {

    private String name;
    private Long voices=0L;

    @Override
    public String toString() {
        return  name + " " + voices;
    }

    public Candidate(String name) {
        this.name = name;
    }

    public Candidate (String name, Long voices) {
        this.name=name;
        this.voices=voices;
    }

    public Candidate() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return name.equals(candidate.name) && voices.equals(candidate.voices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, voices);
    }



}
