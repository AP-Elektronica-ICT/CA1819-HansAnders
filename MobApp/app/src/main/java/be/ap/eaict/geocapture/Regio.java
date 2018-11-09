package be.ap.eaict.geocapture;

import java.util.List;

public class Regio {
    public int Id;
    public String naam;
    public List<Locatie> locaties;
    public int tijd;

    public Regio(String naam, List<Locatie> locaties, int tijd)
    {
        this.naam = naam;
        this.locaties = locaties;
        this.tijd = tijd;
    }
}
