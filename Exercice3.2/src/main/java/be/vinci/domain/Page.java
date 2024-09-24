package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PageImpl.class)
public interface Page {
    int getId();

    void setId(int id);

    String getTitle();

    void setTitle(String title);

    String getUri();

    void setUri(String uri);

    String getContent();

    void setContent(String content);

    String getAuthor();

    void setAuthor(String author);

    String getStatus();

    void setStatus(String status);
}
