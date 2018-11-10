package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Locatie {
    private int Id;
    private String locatienaam;
    private List<Puzzel> puzzels;
    private float lng;
    private float lat;

    public Boolean used = true; //voor de adapter view (grey /white bg)

    public Locatie(String locatienaam, List<Puzzel> puzzels, float lng, float lat)
    {
        this.locatienaam = locatienaam;
        this.puzzels = puzzels;
        this.lng = lng;
        this.lat = lat;
    }

    public String getLocatienaam() {
        return locatienaam;
    }
    public List<Puzzel> getPuzzels() {
        return puzzels;
    }
    public float getLng() {
        return lng;
    }
    public float getLat() {
        return lat;
    }
}
