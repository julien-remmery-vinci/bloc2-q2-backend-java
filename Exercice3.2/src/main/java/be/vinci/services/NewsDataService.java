package be.vinci.services;

import be.vinci.domain.News;
import be.vinci.domain.Page;
import be.vinci.domain.User;

import java.util.List;

public interface NewsDataService {
    News readOne(int id, User user);

    List<News> readAll(User user);
    List<News> readAllForPage(int id, User user);
    News createOne(News news, User user);

    News deleteOne(int id, User user);

    News updateOne(News news, int id, User user);
    News associateOne(int pageId, int newsId, User user);

    int nextNewsId();
}
