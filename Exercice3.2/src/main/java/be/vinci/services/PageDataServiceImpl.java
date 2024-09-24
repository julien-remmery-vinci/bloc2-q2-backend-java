package be.vinci.services;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataServiceImpl implements PageDataService {
    private static final String COLLECTION_NAME = "pages";
    private static final Json<Page> jsonDB = new Json<>(Page.class);

    @Override
    public Page readOne(int id, User user){
        var pages = getPageList();
        Page page = pages.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(page != null && page.getStatus().equals("published"))
            return page;
        if(page != null && page.getStatus().equals("hidden") && page.getAuthor().equals(user.getLogin()))
            return page;
        return null;
    }

    private static List<Page> getPageList() {
      return jsonDB.parse(COLLECTION_NAME);
    }

    @Override
    public List<Page> readAll(User user){
        var pages = getPageList();
        if(user == null)
            return pages.stream().filter(p -> p.getStatus().equals("published")).toList();
        return pages.stream().filter(p -> !p.getStatus().equals("hidden") || p.getAuthor().equals(user.getLogin())).toList();
    }
    @Override
    public Page createOne(Page page, User user){
        if(!page.getAuthor().equals(user.getLogin()))
            return null;
        var pages = getPageList();
        page.setId(nextPageId());
        page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        pages.add(page);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return page;
    }
    @Override
    public Page deleteOne(int id, User user){
        var pages = getPageList();
        Page pageToDelete = pages.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(pageToDelete != null && !pageToDelete.getAuthor().equals(user.getLogin()))
            return null;
        pages.remove(pageToDelete);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return pageToDelete;
    }
    @Override
    public Page updateOne(Page page, int id, User user){
        var pages = getPageList();
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

    @Override
    public int nextPageId() {
        var pages = getPageList();
        return pages.isEmpty() ? 1 : pages.getLast().getId() + 1;
    }
}
