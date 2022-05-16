package co.edu.unbosque.wsrestpinkart.resources;

import co.edu.unbosque.wsrestpinkart.dtos.Colecciones;
import co.edu.unbosque.wsrestpinkart.dtos.ExceptionMessage;
import co.edu.unbosque.wsrestpinkart.dtos.Usuario;
import co.edu.unbosque.wsrestpinkart.services.Operaciones;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Path("/users/{username}/collections")
public class ResourcesArtistas{

    @Context
    ServletContext context;



    //Responde como el método Get de la API de esta clase, recibe como parámetro el nombre del usuario para obtener las colecciones correspondientes a este
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollections(@PathParam("username") String username) throws IOException {
        Operaciones userService = new Operaciones();
        List<Colecciones> col= userService.getColeccionesPorArtista(username);
        return Response.ok().entity(col).build();
    }

    //Responde como el método Post de la API de esta clase, recibe como parámetro el nombre del usuario, el nombre de la colección y la cantidad de NFTs en la colección, bajo estos parámetros crea una colección para dicho usuario
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postCollection(@PathParam("username") String username, @FormParam("collection") String collection, @FormParam("quantity") String quantity) throws IOException {

        String contextPath =context.getRealPath("") + File.separator;
        try {
            Colecciones collection1 = new Operaciones().crearColeccion(username, collection, quantity, contextPath);
            return Response.ok().entity(collection1).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }


    }

    @GET
    @Path("/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("username") String username, @PathParam("collection") String collection) {

        System.out.println(username+collection);
        try {
            Operaciones userService = new Operaciones();
            List<Colecciones> col= userService.getColeccionesPorArtista(username);

            Colecciones collectionList = col.stream()
                    .filter(u -> u.getCollection().equals(collection))
                    .findFirst()
                    .orElse(null);

            if (collectionList != null) {
                return Response.ok()
                        .entity(collectionList)
                        .build();
            } else {
                return Response.status(404)
                        .entity(new ExceptionMessage(404, "User not found"))
                        .build();
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }
}
