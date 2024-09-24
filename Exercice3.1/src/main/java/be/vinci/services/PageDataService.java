package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;

import java.util.List;

public interface PageDataService {
    Page readOne(int id, User user);

    List<Page> readAll(User user);

    Page createOne(Page page, User user);

    Page deleteOne(int id, User user);

    Page updateOne(Page page, int id, User user);

    int nextPageId();
}
