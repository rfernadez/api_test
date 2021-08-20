package model;

public class InvalidComment {
    public InvalidComment(String names, String comment) {
        this.names = names;
        this.comment = comment;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String names;
    private String comment;

}
