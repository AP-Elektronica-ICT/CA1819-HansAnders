package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Game {

    private int ID;
    private int teamamount;
    private Regio regio;
    private long starttijd;

    public List<Team> Teams;
    private List<Locatie> EnabledLocaties;

    public Game(int teamamount, Regio regio, long starttijd, List<Team> teams, List<Locatie> enabledLocaties)
    {
        this.teamamount = teamamount;
        this.regio = regio;
        this.starttijd = starttijd;
        this.Teams = teams;
        this.EnabledLocaties = enabledLocaties;

    }

    public int getTeamamount() {
        return teamamount;
    }

    public Regio getRegio() {
        return regio;
    }

    public long getStarttijd() {
        return starttijd;
    }

    public List<Locatie> getEnabledLocaties() {
        return EnabledLocaties;
    }
}
