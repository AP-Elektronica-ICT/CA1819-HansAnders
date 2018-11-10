package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Game {

    private int ID;
    private int teamamount;
    private Regio regio;
    private int starttijd;

    public List<Team> Teams;
    public List<Locatie> DisabledLocaties;

    public Game(int teamamount, Regio regio, int starttijd, List<Team> teams, List<Locatie> disabledLocaties)
    {
        this.teamamount = teamamount;
        this.regio = regio;
        this.starttijd = starttijd;
        this.Teams = teams;
        this.DisabledLocaties = disabledLocaties;

    }

    public int getTeamamount() {
        return teamamount;
    }

    public Regio getRegio() {
        return regio;
    }

    public int getStarttijd() {
        return starttijd;
    }
}
