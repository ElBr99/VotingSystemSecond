package voting.dto;

public class VotingDto {

    private String title;

    public VotingDto(String title) {
        this.title = title;
    }

    public VotingDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
