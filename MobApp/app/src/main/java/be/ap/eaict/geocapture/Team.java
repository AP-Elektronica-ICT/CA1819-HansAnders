package be.ap.eaict.geocapture;

import java.util.List;

public class Team {
    public int Id ;
    public List<User> Users ;
    public List<Locatie> CapturedLocaties;
    public String TeamName ;

    public Team(List<User> Users, List<Locatie> capturedLocaties, String teamName)
    {
        this.Users = Users;
        this.CapturedLocaties = capturedLocaties;
        this.TeamName = teamName;
    }
}
