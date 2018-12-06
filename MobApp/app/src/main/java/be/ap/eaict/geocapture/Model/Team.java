package be.ap.eaict.geocapture.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public int Id ;
    public List<User> Users ;
    public List<Locatie> CapturedLocaties;
    public String TeamName ;


    public Team()
    {
        this.Users = new ArrayList<>();
        this.TeamName = "zoibfkd";// randomize this shit or sth
    }

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
