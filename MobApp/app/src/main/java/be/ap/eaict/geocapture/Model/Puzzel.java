package be.ap.eaict.geocapture.Model;

public class Puzzel {
    public int id;
    public String vraag;
    public String antwoord;

    public Puzzel(String omschrijving, String antwoord)
    {
        this.vraag = omschrijving;
        this.antwoord = antwoord;
    }

    public String getVraag() {
        return vraag;
    }

    public String getAntwoord() {
        return antwoord;
    }
}
