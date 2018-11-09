package be.ap.eaict.geocapture;

public class User {
    public int Id ;
    public String Name;
    public float lng;
    public float lat;

    public User(String name, float lng, float lat)
    {
        this.Name = name;
        this.lng = lng;
        this.lat = lat;
    }
}
