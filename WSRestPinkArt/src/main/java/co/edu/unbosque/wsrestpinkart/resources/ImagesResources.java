package co.edu.unbosque.wsrestpinkart.resources;

import co.edu.unbosque.wsrestpinkart.dtos.Obras;
import co.edu.unbosque.wsrestpinkart.dtos.Usuario;
import co.edu.unbosque.wsrestpinkart.services.Operaciones;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.util.*;

@Path("/users/{username}/collections/{collection}/arts")
public class ImagesResources {
    @Context
    ServletContext context;
    private String UPLOAD_DIRECTORY = "OBRAS";
    private Operaciones uService;


    //Retorna las imagenes en un JSON de un usuario y una colección en específico
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response personalListFiles(@PathParam("username") String username,@PathParam("collection") String collectionName) {

        uService = new Operaciones();

        List<Obras> imag = null;


        try {
            List<Obras> imagenes = new ArrayList<Obras>();

            imag = uService.getObra();

            for(Obras nft: imag){

                if(nft.getEmail_owner().equals(username) && nft.getCollection().equals(collectionName)){
                    nft.setId(UPLOAD_DIRECTORY + File.separator + nft.getId());
                    imagenes.add(nft);
                }
            }
            return Response.ok().entity(imagenes).build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

    //Crea NFTs de un usuario y una colección en específico
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadNFT(MultipartFormDataInput inputData) {

        uService = new Operaciones();

        try {

            String emailAuthor = inputData.getFormDataPart("author", String.class, null);
            String collection = inputData.getFormDataPart("collection", String.class, null);
            String title = inputData.getFormDataPart("title", String.class, null);
            String price = inputData.getFormDataPart("price", String.class, null);

            System.out.println(title);
            //Found Data author
            List<Usuario> users = new Operaciones().getUsers();
            Usuario userFounded = users.stream().filter(user -> emailAuthor.equals(user.getUsername()))
                    .findFirst().orElse(null);
            String author = userFounded.getUsername();

            Map<String, List<InputPart>> formParts = inputData.getFormDataMap();
            List<InputPart> inputParts = formParts.get("customFile");

            for (InputPart inputPart : inputParts) {
                try {
                    // Retrieving headers and reading the Content-Disposition header to file name
                    MultivaluedMap<String, String> headers = inputPart.getHeaders();
                    String randomString = uService.generateRandomString();
                    String fileName = randomString+"&"+title+parseFileName(headers).split("\\.")[1];


                    // Handling the body of the part with an InputStream
                    InputStream istream = inputPart.getBody(InputStream.class,null);

                    saveFile(istream, fileName, context);

                    uService.crearObra(fileName, collection, title, author, price, emailAuthor, context.getRealPath("") +File.separator);
                } catch (IOException e) {
                    return Response.serverError().build();
                }
            }
        } catch (IOException e) {
            return Response.serverError().build();
        }
        return Response.status(201)
                .entity("Obra subida exitosamente")
                .build();
    }

    //Retorna el nombre del archivo del header del multipartFormDataInput
    private String parseFileName(MultivaluedMap<String, String> headers) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                String fileName = tmp[1].trim().replaceAll("\"","");
                return fileName;
            }
        }
        return "desconocido";
    }

    //Guarda el archivo subido a una ruta específica en el servidor
    private void saveFile(InputStream uploadedInputStream, String fileName, ServletContext context) {
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            // Complementing servlet path with the relative path on the server
            String uploadPath = context.getRealPath("") +File.separator+ UPLOAD_DIRECTORY+File.separator;

            // Creating the upload folder, if not exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Persisting the file by output stream
            OutputStream outpuStream = new FileOutputStream(uploadPath + fileName);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
