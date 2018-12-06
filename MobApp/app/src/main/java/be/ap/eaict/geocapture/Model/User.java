package be.ap.eaict.geocapture.Model;

public class User {
    public int id;
    public String name;
    public float lng;
    public float lat;

    public User(String name, float lng, float lat)
    {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }
}
