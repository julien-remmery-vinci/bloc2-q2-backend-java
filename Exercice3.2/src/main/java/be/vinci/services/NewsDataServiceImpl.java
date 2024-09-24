package be.vinci.services;

import be.vinci.domain.News;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import jakarta.inject.Inject;
import org.apache.commons.text.StringEscapeUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class NewsDataServiceImpl implements NewsDataService {
    private static final String COLLECTION_NAME = "news";
    private static final Json<News> jsonDB = new Json<>(News.class);
    @Inject
    PageDataService pageDataService;

    @Override
    public News readOne(int id, User user){
        var pages = getNewsList();
        News news = pages.stream().filter(n -> n.getId() == id).findAny().orElse(null);
        if(news != null && news.getStatus().equals("published"))
            return news;
        if(news != null && news.getStatus().equals("hidden") && news.getAuthor().equals(user.getLogin()))
            return news;
        return null;
    }
    @Override
    public List<News> readAll(User user){
        var newsList = getNewsList();
        if(user == null)
            return newsList.stream().filter(n -> n.getStatus().equals("published")).toList();
        return newsList.stream().filter(n -> !n.getStatus().equals("hidden") || n.getAuthor().equals(user.getLogin())).toList();
    }

    private static List<News> getNewsList() {
      return jsonDB.parse(COLLECTION_NAME);
    }

    @Override
    public List<News> readAllForPage(int id, User user) {
        var newsList = getNewsList();
        if (user == null)
            return newsList.stream().filter(n -> n.getAssociatedPage() == id && n.getStatus().equals("published")).toList();
        return newsList.stream().filter(n -> n.getAssociatedPage() == id && n.getAuthor().equals(user.getLogin())).toList();
    }

    @Override
    public News createOne(News news, User user){
        if(!news.getAuthor().equals(user.getLogin()))
            return null;
        var newsList = getNewsList();
        news.setId(nextNewsId());
        news.setTitle(StringEscapeUtils.escapeHtml4(news.getTitle()));
        news.setDescription(StringEscapeUtils.escapeHtml4(news.getDescription()));
        news.setContent(StringEscapeUtils.escapeHtml4(news.getContent()));
        news.setAuthor(StringEscapeUtils.escapeHtml4(news.getAuthor()));
        news.setCreatedDateTime(Date.from(Instant.now()));
        news.setExpireDateTime(Date.from(Instant.now()));
        newsList.add(news);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return news;
    }

    @Override
    public News deleteOne(int id, User user){
        var newsList = getNewsList();
        News newsToDelete = newsList.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(newsToDelete != null && !newsToDelete.getAuthor().equals(user.getLogin()))
            return null;
        newsList.remove(newsToDelete);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return newsToDelete;
    }

    @Override
    public News updateOne(News news, int id, User user){
        var newsList = getNewsList();
        News newsToUpdate = newsList.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(newsToUpdate == null || !newsToUpdate.getAuthor().equals(user.getLogin()))
            return null;
        news.setId(id);
        news.setTitle(StringEscapeUtils.escapeHtml4(news.getTitle()));
        news.setDescription(StringEscapeUtils.escapeHtml4(news.getDescription()));
        news.setContent(StringEscapeUtils.escapeHtml4(news.getContent()));
        news.setAuthor(StringEscapeUtils.escapeHtml4(news.getAuthor()));
        newsList.remove(news);
        newsList.add(news);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return news;
    }
    @Override
    public News associateOne(int pageId, int newsId, User user){
        var newsList = getNewsList();
        News newsToAssociate = newsList.stream().filter(p -> p.getId() == newsId).findAny().orElse(null);
        Page pageToAssociate = pageDataService.readOne(pageId, user);
        if(newsToAssociate == null || pageToAssociate == null)
            return null;
        newsToAssociate.setAssociatedPage(pageId);
        newsList.remove(newsToAssociate);
        newsList.add(newsToAssociate);
        jsonDB.serialize(newsList, COLLECTION_NAME);
        return newsToAssociate;
    }
    @Override
    public int nextNewsId() {
        var news = getNewsList();
        return news.isEmpty() ? 1 : news.getLast().getId() + 1;
    }
}
