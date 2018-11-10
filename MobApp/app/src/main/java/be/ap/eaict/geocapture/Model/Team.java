package be.ap.eaict.geocapture.Model;

import java.util.List;

public class Team {
    private int Id ;
    private List<User> Users ;
    private List<Locatie> CapturedLocaties;
    private String TeamName ;

    public Team(List<User> Users, List<Locatie> capturedLocaties, String teamName)
    {
        this.Users = Users;
        this.CapturedLocaties = capturedLocaties;
        this.TeamName = teamName;
    }

    public List<User> getUsers() {
        return Users;
    }

    public List<Locatie> getCapturedLocaties() {
        return CapturedLocaties;
    }

    public String getTeamName() {
        return TeamName;
    }
}
