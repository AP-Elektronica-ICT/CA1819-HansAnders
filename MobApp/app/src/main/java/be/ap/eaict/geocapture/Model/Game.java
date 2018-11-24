package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Game {

    private int ID;
    private Regio regio;
    private long starttijd;

    public List<Team> Teams;
    private List<Locatie> EnabledLocaties;

    public Game(Regio regio, long starttijd, List<Team> teams, List<Locatie> enabledLocaties)
    {
        this.regio = regio;
        this.starttijd = starttijd;
        this.Teams = teams;
        this.EnabledLocaties = enabledLocaties;

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
