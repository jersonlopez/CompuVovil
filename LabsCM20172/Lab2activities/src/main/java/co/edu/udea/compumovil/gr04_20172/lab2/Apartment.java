package co.edu.udea.compumovil.gr04_20172.lab2;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerson.lopez on 21/09/17.
 */

public class Apartment {
    //int photoId;
    String type;
    String price;
    String area;
    String description;
    String ubication;
    Bitmap photo;
    int id;


    Apartment(Bitmap photo, String type, String price, String area, String description, String ubication, int id) {
        this.photo = photo;
        this.type = type;
        this.price = price;
        this.area = area;
        this.description = description;
        this.ubication = ubication;
        this.id = id;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getUbitacion() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.type = ubication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

