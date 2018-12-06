package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Locatie {
    public int id;
    public String locatienaam;
    public List<Puzzel> puzzels;
    public float lng;
    public float lat;

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
