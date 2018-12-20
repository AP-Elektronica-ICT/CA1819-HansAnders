package be.ap.eaict.geocapture.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public int id ;
    public List<User> users;
    public List<Locatie> capturedLocaties;
    public String teamName;


    public Team()
    {
        this.users = new ArrayList<>();
        this.teamName = "zoibfkd";// randomize this shit or sth
    }

    public Team(List<User> Users, List<Locatie> capturedLocaties, String teamName)
    {
        this.users = Users;
        this.capturedLocaties = capturedLocaties;
        this.teamName = teamName;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Locatie> getCapturedLocaties() {
        return capturedLocaties;
    }

    public String getTeamName() {
        return teamName;
    }
}
