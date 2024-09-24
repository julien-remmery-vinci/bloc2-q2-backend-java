package be.vinci.domain;

import java.util.Objects;

public class Page {
    private int id;
    private String title;
    private String uri;
    private String content;
    private String author;
    private String status;
    public Page(){}

    public Page(int id, String title, String uri, String content, String author, String status) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.content = content;
        this.author = author;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(!status.equals("hidden") && !status.equals("published"))
            this.status = null;
        else
            this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return id == page.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
