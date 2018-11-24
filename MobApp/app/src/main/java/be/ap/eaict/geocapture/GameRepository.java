package be.ap.eaict.geocapture;

import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;
import be.ap.eaict.geocapture.Model.Team;

public class GameRepository implements IGameRepository{

    private static IGameRepository repo = null;

    private static IGameRepository getInstance() {
        if (repo == null)
        {
            repo = new GameRepository();
        }
        return repo;
    }

    private String userName;
    private int userKey; // api should return a key it should use to identify the user or sth
    private int team;
    private int lobbyId;


    @Override
    public List<Regio> getRegios() {
        List<Regio> regios = new ArrayList<>();

        //API CALL
        return regios;
    }

    public boolean JoinGame(String username, int team, int lobbyId)
    {

        //try to join game with information:  txtTeam, txtLobbyId, txtName is valid by doing API CALL
        //if returns false, game can't be started, API call should return the error information (game doesn't exist, name already in use, can't join team xx....)

        if(true)//api call returns true or false (.../api/game/join/id)
        {
            this.userName = username;
            this.team = team;
            this.lobbyId = lobbyId;
        }

        return false;
    }

    public void startGame(int teams)//new lobby that people can join
    {
        List<Team> listTeams = new ArrayList<>();
        for(int i =0; i< teams; i++)
        {
           listTeams.add(new Team());//empty teams initiated
        }
        Game startgame = new Game(null,System.currentTimeMillis(), listTeams, null);
        //POST startgame

        //api call to create new game and create lobby id so people can join
        // API POST EMPTY GAME! --> WILL RETURN GAME WITH ID

        this.lobbyId = 0;// << -----
    }

    @Override
    public void createGame(Regio regio, List<Locatie> enabledlocaties, String userName) {
        Game game = getGame();
        game = new Game(regio, game.getStarttijd(), game.Teams, game.getEnabledLocaties());
        //API CALL to create game in backend

        //API PUT game (.../api/game/id)

        this.JoinGame(userName,0,this.lobbyId); // host joins team 0 by default
    }

    @Override
    public Game getGame() {

        //API CALL for game information

        return null;
    }


}
