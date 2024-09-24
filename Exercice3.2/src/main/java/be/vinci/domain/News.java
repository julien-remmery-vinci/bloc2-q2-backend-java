package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonDeserialize(as = NewsImpl.class)
public interface News {
    int getId();

    void setId(int id);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getContent();

    void setContent(String content);

    String getAuthor();

    void setAuthor(String author);

    String getStatus();

    void setStatus(String status);

    Date getCreatedDateTime();

    void setCreatedDateTime(Date createdDateTime);

    Date getExpireDateTime();

    void setExpireDateTime(Date expireDateTime);

    int getAssociatedPage();

    void setAssociatedPage(int associatedPage);

    int getPosition();

    void setPosition(int position);
}
