package be.ap.eaict.geocapture.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public int id ;
    public List<User> users;
    //public List<Locatie> capturedLocaties;
    public List<CaptureLocatie> capturedLocaties;
    public String teamName;


    public Team()
    {
        this.users = new ArrayList<>();
        this.teamName = "een cloud team";// randomize this shit or sth
    }

    public Team(List<User> Users, List<CaptureLocatie> capturedLocaties, String teamName)
    {
        this.users = Users;
        this.capturedLocaties = capturedLocaties;
        this.teamName = teamName;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<CaptureLocatie> getCapturedLocaties() {
        return capturedLocaties;
    }

    public String getTeamName() {
        return teamName;
    }
}
