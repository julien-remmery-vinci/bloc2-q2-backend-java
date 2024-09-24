package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataService {
    private static final String COLLECTION_NAME = "pages";
    private static final Json<Page> jsonDB = new Json<>(Page.class);

    public Page readOne(int id, User user){
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page page = pages.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(page != null && page.getStatus().equals("published"))
            return page;
        if(page != null && page.getStatus().equals("hidden") && page.getAuthor().equals(user.getLogin()))
            return page;
        return null;
    }
    public List<Page> readAll(User user){
        var pages = jsonDB.parse(COLLECTION_NAME);
        if(user == null)
            return pages.stream().filter(p -> p.getStatus().equals("published")).toList();
        return pages.stream().filter(p -> !p.getStatus().equals("hidden") || p.getAuthor().equals(user.getLogin())).toList();
    }
    public Page createOne(Page page, User user){
        if(!page.getAuthor().equals(user.getLogin()))
            return null;
        var pages = jsonDB.parse(COLLECTION_NAME);
        page.setId(nextPageId());
        page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        pages.add(page);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return page;
    }
    public Page deleteOne(int id, User user){
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageToDelete = pages.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(pageToDelete != null && !pageToDelete.getAuthor().equals(user.getLogin()))
            return null;
        pages.remove(pageToDelete);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return pageToDelete;
    }
    public Page updateOne(Page page, int id, User user){
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageToUpdate = pages.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(pageToUpdate == null || !pageToUpdate.getAuthor().equals(user.getLogin()))
            return null;
        page.setId(id);
        page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        pages.remove(page);
        pages.add(page);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return page;
    }

    public int nextPageId() {
        var pages = jsonDB.parse(COLLECTION_NAME);
        if (pages.isEmpty())
            return 1;
        return pages.getLast().getId() + 1;
    }
}