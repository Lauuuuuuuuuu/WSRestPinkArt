package co.edu.unbosque.wsrestpinkart.services;
import co.edu.unbosque.wsrestpinkart.dtos.Colecciones;
import co.edu.unbosque.wsrestpinkart.dtos.Obras;
import co.edu.unbosque.wsrestpinkart.dtos.Usuario;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Operaciones {

    private static String ruta = "usuarios.csv";

    public List<Usuario> getUsers() throws IOException {

        List<Usuario> usuarios;

        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Usuarios.csv")) {

            HeaderColumnNameMappingStrategy<Usuario> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Usuario.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Usuario> csvToBean = new CsvToBeanBuilder<Usuario>(br)
                        .withType(Usuario.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                usuarios = csvToBean.parse();
            }
        }

        return usuarios;
    }


    public Usuario crearUsuario(String username, String password, String role, String coins, String path) throws IOException {
        String newLine = username + "," + password + "," + role + "," + coins + "\n";

        System.out.println(path + File.separator + "resources" + File.separator + "users.csv" + "Create");
        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes" + File.separator + "usuarios.csv",true);
        os.write(newLine.getBytes());
        os.close();
   return new Usuario(username,password,role,coins);
    }

    public List<Colecciones> getUltimasColecciones() throws IOException {

        List<Colecciones> collectionList;
        List<Colecciones> respuesta = new ArrayList<>();


        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Colecciones.csv")) {

            HeaderColumnNameMappingStrategy<Colecciones> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Colecciones.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Colecciones> csvToBean = new CsvToBeanBuilder<Colecciones>(br)
                        .withType(Colecciones.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                collectionList = csvToBean.parse();
                Collections.reverse(collectionList);

                int numero=6;
                if(collectionList.size()<numero){
                    numero=collectionList.size();
                }
                for(int x=0;x<numero;x++){
                    respuesta.add(collectionList.get(x));
                }

            }
        }

        return respuesta;
    }

    public List<Colecciones> getColeccionesPorArtista(String username) throws IOException {

        List<Colecciones> collectionList;

        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Colecciones.csv")) {

            HeaderColumnNameMappingStrategy<Colecciones> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Colecciones.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Colecciones> csvToBean = new CsvToBeanBuilder<Colecciones>(br)
                        .withType(Colecciones.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                collectionList = csvToBean.parse().stream().filter(collection -> collection.getUsername().equals(username)).collect(Collectors.toList());
            }
        }

        return collectionList;
    }

    public List<Colecciones> getColecciones() throws IOException {

        List<Colecciones> collectionList;

        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Colecciones.csv")) {

            HeaderColumnNameMappingStrategy<Colecciones> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Colecciones.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Colecciones> csvToBean = new CsvToBeanBuilder<Colecciones>(br)
                        .withType(Colecciones.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                collectionList = csvToBean.parse();
            }
        }

        return collectionList;
    }

    //Obtiene un array correspondiente a la lista de NFTs que se encuentran creados en la plataforma
    public List<Obras> getObra() throws IOException {

        List<Obras> obras;

        try (InputStream is = Operaciones.class.getClassLoader()
                .getResourceAsStream("Nfts.csv")) {

            HeaderColumnNameMappingStrategy<Obras> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Obras.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<Obras> csvToBean = new CsvToBeanBuilder<Obras>(br)
                        .withType(Obras.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                obras = csvToBean.parse();
            }
        }
        return obras;
    }

    public void crearObra(String id, String collection, String title, String author, String price, String email_owner, String path) throws IOException {
        String newLine = id + "," + collection + "," + title + ","+author+ "," + price + ","+"0"+","+ email_owner +"\n";


        String fullpath = path +"WEB-INF"+File.separator+ "classes"+ File.separator+"Nfts.csv";
        FileOutputStream os = new FileOutputStream( fullpath, true);
        os.write(newLine.getBytes());
        os.close();

    }

    public Colecciones crearColeccion(String username,String collection,String quantity,String path) throws IOException {
        String newLine = username + "," + collection + "," + quantity+"\n";
        String fullpath = path + "WEB-INF"+File.separator+"classes" + File.separator+ "Collections.csv";
        System.out.println(fullpath);
        FileOutputStream os = new FileOutputStream(fullpath, true);
        os.write(newLine.getBytes());
        os.close();

        return new Colecciones(username,collection,quantity);
    }

    //Genera un String de caracteres aleatorios
    public String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}

