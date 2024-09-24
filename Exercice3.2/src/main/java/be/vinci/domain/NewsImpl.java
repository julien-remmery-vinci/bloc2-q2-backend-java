package be.vinci.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

class NewsImpl implements News {
    private int id;
    private String title;
    private String description;
    private String content;
    private String author;
    private String status;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdDateTime;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date expireDateTime;
    private int associatedPage;
    private int position;

    public NewsImpl(){}
    public NewsImpl(int id, String title, String description, String content, String author, String status, Date createdDateTime, Date expireDateTime, Integer associatedPage, int position) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
        this.status = status;
        this.createdDateTime = createdDateTime;
        this.expireDateTime = expireDateTime;
        this.associatedPage = associatedPage;
        this.position = position;
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public Date getExpireDateTime() {
        return expireDateTime;
    }

    @Override
    public void setExpireDateTime(Date expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    @Override
    public int getAssociatedPage() {
        return associatedPage;
    }

    @Override
    public void setAssociatedPage(int associatedPage) {
        this.associatedPage = associatedPage;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsImpl news = (NewsImpl) o;
        return id == news.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
