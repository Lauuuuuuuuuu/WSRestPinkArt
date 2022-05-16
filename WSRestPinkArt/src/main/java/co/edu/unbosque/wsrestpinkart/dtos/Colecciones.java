package co.edu.unbosque.wsrestpinkart.dtos;

import com.opencsv.bean.CsvBindByName;

public class Colecciones {
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String collection;
    @CsvBindByName
    private String quantity;

    public Colecciones(){

    }

    public Colecciones(String username, String collection, String quantity) {
        this.username = username;
        this.collection = collection;
        this.quantity = quantity;
    }

    //Obtiene el valor correspondiente a la variable username
    public String getUsername() {return username;}

    //Obtiene el valor correspondiente a la variable collection
    public String getCollection() {return collection;}

    //Obtiene el valor correspondiente a la variable quantity
    public String getQuantity() {return quantity;}
}
