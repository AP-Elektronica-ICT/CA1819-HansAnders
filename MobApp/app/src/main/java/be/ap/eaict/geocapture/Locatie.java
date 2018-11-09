package be.ap.eaict.geocapture;

import java.util.List;

public class Locatie {
    public int Id;
    public String locatienaam;
    public List<Puzzel> puzzels;
    public float lng;
    public float lat;
    public Locatie(String locatienaam, List<Puzzel> puzzels, float lng, float lat)
    {
        this.locatienaam = locatienaam;
        this.puzzels = puzzels;
        this.lng = lng;
        this.lat = lat;
    }
}
