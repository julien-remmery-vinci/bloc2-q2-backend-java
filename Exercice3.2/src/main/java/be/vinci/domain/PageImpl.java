package be.vinci.domain;

import java.util.Objects;

class PageImpl implements Page {
    private int id;
    private String title;
    private String uri;
    private String content;
    private String author;
    private String status;
    public PageImpl(){}

    public PageImpl(int id, String title, String uri, String content, String author, String status) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.content = content;
        this.author = author;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
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
        PageImpl page = (PageImpl) o;
        return id == page.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
