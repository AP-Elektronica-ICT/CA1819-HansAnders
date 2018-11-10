package be.ap.eaict.geocapture.Model;

public class Puzzel {
    private int Id;
    private String Omschrijving;
    private String antwoord;

    public Puzzel(String omschrijving, String antwoord)
    {
        this.Omschrijving = omschrijving;
        this.antwoord = antwoord;
    }

    public String getOmschrijving() {
        return Omschrijving;
    }

    public String getAntwoord() {
        return antwoord;
    }
}
