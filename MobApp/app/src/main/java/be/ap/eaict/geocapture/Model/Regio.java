package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Regio {
    private int Id;
    private String naam;
    private List<Locatie> locaties;
    private int tijd;

    public Regio(String naam, List<Locatie> locaties, int tijd)
    {
        this.naam = naam;
        this.locaties = locaties;
        this.tijd = tijd;
    }

    public String getNaam() {
        return naam;
    }

    public List<Locatie> getLocaties() {
        return locaties;
    }

    public int getTijd() {
        return tijd;
    }
}
