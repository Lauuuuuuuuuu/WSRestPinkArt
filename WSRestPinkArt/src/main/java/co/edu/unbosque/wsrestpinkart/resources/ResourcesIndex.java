package co.edu.unbosque.wsrestpinkart.resources;
import co.edu.unbosque.wsrestpinkart.dtos.Colecciones;
import co.edu.unbosque.wsrestpinkart.services.Operaciones;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Path("/collections")
public class ResourcesIndex {

    //Responde como el método Get de la API de esta clase, obtiene las últimas seis colecciones agregadas a la plataforma
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimasCollections() throws IOException {
        Operaciones userServices = new Operaciones();
        List<Colecciones> collections = userServices.getUltimasColecciones();
        return Response.ok().entity(collections).build();
    }

}
