package be.vinci.api;

import be.vinci.api.filters.AnonymousOrAuthorized;
import be.vinci.api.filters.Authorize;
import be.vinci.domain.News;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.NewsDataService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;

@Singleton
@Path("news")
public class NewsRessource {
    @Inject
    private NewsDataService newsDataService;

    @GET
    @AnonymousOrAuthorized
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News readOne(@PathParam("id") int id, @Context ContainerRequest request){
        News newsFound = newsDataService.readOne(id, (User) request.getProperty("user"));
        if(newsFound == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return newsFound;
    }

    @GET
    @AnonymousOrAuthorized
    @Path("/page/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> readAllForPage(@PathParam("id") int id, @Context ContainerRequest request){
        List<News> newsFound = newsDataService.readAllForPage(id, (User) request.getProperty("user"));
        if(newsFound == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return newsFound;
    }

    @GET
    @AnonymousOrAuthorized
    @Produces(MediaType.APPLICATION_JSON)
    public List<News> readAll(@Context ContainerRequest request){
        return newsDataService.readAll((User) request.getProperty("user"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public News createOne(News news, @Context ContainerRequest request){
        if(news.getStatus() == null)
            throw new WebApplicationException("The status can only be 'hidden' or 'published'", Response.Status.BAD_REQUEST);
        News createdNews = newsDataService.createOne(news, (User) request.getProperty("user"));
        if(createdNews == null)
            throw new WebApplicationException("The logged user must match the provided author", Response.Status.BAD_REQUEST);
        return createdNews;
    }

    @DELETE
    @Path("/{id}")
    @Authorize
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public News deleteOne(@PathParam("id") int id, @Context ContainerRequest request){
        if (id == 0) // default value of an integer => has not been initialized
            throw new WebApplicationException("Lacks of mandatory id info", Response.Status.BAD_REQUEST);
        News deletedNews = newsDataService.deleteOne(id, (User) request.getProperty("user"));
        if (deletedNews == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return deletedNews;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public News updateOne(News news, @PathParam("id") int id, @Context ContainerRequest request) {
        if (id == 0 || news.getStatus() == null)
            throw new WebApplicationException("Lacks of mandatory info", Response.Status.BAD_REQUEST);
        News updatedNews = newsDataService.updateOne(news, id, (User) request.getProperty("user"));
        if (updatedNews == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return updatedNews;
    }

    @POST
    @Path("/{id}/associate")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public News associateOne(JsonNode json, @PathParam("id") int id, @Context ContainerRequest request){
        if (id == 0)
            throw new WebApplicationException("Lacks of mandatory id info", Response.Status.BAD_REQUEST);
        if(!json.hasNonNull("pageId"))
            throw new WebApplicationException("A valid pageId must be given", Response.Status.BAD_REQUEST);
        News associatedNews = newsDataService.associateOne(json.get("pageId").asInt(), id, (User) request.getProperty("user"));
        if(associatedNews == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return associatedNews;
    }
}
