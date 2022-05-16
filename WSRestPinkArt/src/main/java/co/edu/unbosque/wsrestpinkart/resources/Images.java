package co.edu.unbosque.wsrestpinkart.resources;

import co.edu.unbosque.wsrestpinkart.dtos.Obras;
import co.edu.unbosque.wsrestpinkart.services.Operaciones;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Path("/arts")
public class Images {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "OBRAS";
    private Operaciones uService;

    //Retorna los últimos seis NFTs en un JSON de un usuario y una colección en específico
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response generalListFiles() {

        uService = new Operaciones();

        List<Obras> obras = null;
        try {
            obras = uService.getObra();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Obras> dataFiles = new ArrayList<Obras>();

        Collections.reverse(obras);
        for(int j=0;j<6 && j<obras.size();j++){
            dataFiles.add(obras.get(j));
            dataFiles.get(j).setId(UPLOAD_DIRECTORY + File.separator + dataFiles.get(j).getId());
        }

        // Adding the data to response, parsing it to json using Gson library
        return Response.ok().entity(dataFiles).build();
    }
}
