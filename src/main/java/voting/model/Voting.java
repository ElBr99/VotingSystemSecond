package voting.model;

import java.util.List;

public class Voting {

    private String title;
    private int id;

    public Voting(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Voting() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
