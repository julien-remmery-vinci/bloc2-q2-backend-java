package be.vinci;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

@Path("texts")
public class TextResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Text> readAll(@DefaultValue("") @QueryParam("level") String level){
        var texts = Json.parse();
        if(!level.isBlank()){
            List<Text> filteredTexts = texts.stream().filter(text -> text.getLevel().equals(level)).toList();
            return filteredTexts;
        }
        return texts;
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Text readOne(@PathParam("id") int id){
        var texts = Json.parse();
        Text textFound = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        if(textFound == null){
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found")
                    .type("text/plain")
                    .build());
        }
        return textFound;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Text createOne(Text text){
        if(text == null || (!text.getLevel().equals("easy")
                && !text.getLevel().equals("medium")
                && !text.getLevel().equals("hard")))
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Lacks of mandatory info or unauthorized text level")
                            .type("text/plain")
                            .build());
        var texts = Json.parse();
        text.setId(texts.size() + 1);
        text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
        text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
        texts.add(text);
        Json.serialize(texts);
        return text;
    }
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Text deleteOne(@PathParam("id") int id){
        var texts = Json.parse();
        Text textToDelete =texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        if(textToDelete == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        texts.remove(textToDelete);
        Json.serialize(texts);
        return textToDelete;
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Text updateOne(Text text, @PathParam("id") int id){
        if (id == 0 || text == null || text.getContent() == null || text.getContent().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Lacks of mandatory info")
                            .type("text/plain")
                            .build());
        if(text.getLevel() == null || (!text.getLevel().equals("easy")
                && !text.getLevel().equals("medium")
                && !text.getLevel().equals("hard")))
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Lacks of mandatory info or unauthorized text level")
                            .type("text/plain")
                            .build());
        var texts = Json.parse();
        Text textToUpdate = texts.stream().filter(t -> t.getId() == id).findAny().orElse(null);
        if(textToUpdate == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        text.setId(id);
        text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
        text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
        texts.remove(text);
        texts.add(text);
        Json.serialize(texts);
        return text;
    }
}
