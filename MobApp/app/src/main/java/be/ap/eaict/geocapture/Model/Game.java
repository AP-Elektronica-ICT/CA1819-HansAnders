package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Game {

    public int id;
    public  Regio regio;
    public  long starttijd;

    public List<Team> teams;
    public  List<Locatie> enabledLocaties;

    public Game(Regio regio, long starttijd, List<Team> teams, List<Locatie> enabledLocaties)
    {
        this.regio = regio;
        this.starttijd = starttijd;
        this.teams = teams;
        this.enabledLocaties = enabledLocaties;

    }


    public Regio getRegio() {
        return regio;
    }

    public long getStarttijd() {
        return starttijd;
    }

    public List<Locatie> getEnabledLocaties() {
        return enabledLocaties;
    }
}
