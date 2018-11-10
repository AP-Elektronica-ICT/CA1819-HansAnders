package be.ap.eaict.geocapture.Model;

public class User {
    private int Id ;
    private String Name;
    private float lng;
    private float lat;

    public User(String name, float lng, float lat)
    {
        this.Name = name;
        this.lng = lng;
        this.lat = lat;
    }

    public String getName() {
        return Name;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }
}
