package co.edu.unbosque.restpinkart.dtos;

import java.io.File;

public class ObrasArte {


    private String usernamecreator;
    private String title;
    private String price;
    private String filename;

    public String getUsernamecreator() {
        return usernamecreator;
    }

    public void setUsernamecreator(String usernamecreator) {
        this.usernamecreator = usernamecreator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

