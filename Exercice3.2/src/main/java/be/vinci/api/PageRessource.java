package be.vinci.api;

import be.vinci.api.filters.AnonymousOrAuthorized;
import be.vinci.api.filters.Authorize;
import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.PageDataService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;

@Singleton
@Path("pages")
public class PageRessource {
    @Inject
    private PageDataService pageDataService;

    @GET
    @AnonymousOrAuthorized
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Page readOne(@PathParam("id") int id, @Context ContainerRequest request){
        Page pageFound = pageDataService.readOne(id, (User) request.getProperty("user"));
        if(pageFound == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return pageFound;
    }

    @GET
    @AnonymousOrAuthorized
    @Produces(MediaType.APPLICATION_JSON)
    public List<Page> readAll(@Context ContainerRequest request){
        return pageDataService.readAll((User) request.getProperty("user"));
    }

    @POST
    @Authorize
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Page createOne(Page page, @Context ContainerRequest request){
        if(page.getStatus() == null)
            throw new WebApplicationException("The status can only be 'hidden' or 'published'", Response.Status.BAD_REQUEST);
        Page createdPage = pageDataService.createOne(page, (User) request.getProperty("user"));
        if(createdPage == null)
            throw new WebApplicationException("The logged user must match the provided author", Response.Status.BAD_REQUEST);
        return createdPage;
    }

    @DELETE
    @Path("/{id}")
    @Authorize
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Page deleteOne(@PathParam("id") int id, @Context ContainerRequest request){
        if (id == 0) // default value of an integer => has not been initialized
            throw new WebApplicationException("Lacks of mandatory id info", Response.Status.BAD_REQUEST);
        Page deletedPage = pageDataService.deleteOne(id, (User) request.getProperty("user"));
        if (deletedPage == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return deletedPage;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page updateOne(Page page, @PathParam("id") int id, @Context ContainerRequest request) {
        if (id == 0 || page.getStatus() == null)
            throw new WebApplicationException("Lacks of mandatory info", Response.Status.BAD_REQUEST);
        Page updatedPage = pageDataService.updateOne(page, id, (User) request.getProperty("user"));
        if (updatedPage == null)
            throw new WebApplicationException("Ressource not found", Response.Status.NOT_FOUND);
        return updatedPage;
    }
}