package model;

public class InvalidPost {
    public InvalidPost(String titles, String content) {
        this.titles = titles;
        this.content = content;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String titles;
    private String content;

}
