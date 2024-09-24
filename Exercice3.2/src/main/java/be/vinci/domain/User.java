package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface User {
    String getLogin();

    void setLogin(String login);

    int getId();

    void setId(int id);

    String getPassword();

    void setPassword(String password);

    boolean checkPassword(String password);

    String hashPassword(String password);
}
