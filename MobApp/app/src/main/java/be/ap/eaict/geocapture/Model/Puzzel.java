package be.ap.eaict.geocapture.Model;

public class Puzzel {
    private int Id;
    private String Vraag;
    private String Antwoord;

    public Puzzel(String omschrijving, String antwoord)
    {
        this.Vraag = omschrijving;
        this.Antwoord = antwoord;
    }

    public String getVraag() {
        return Vraag;
    }

    public String getAntwoord() {
        return Antwoord;
    }
}
