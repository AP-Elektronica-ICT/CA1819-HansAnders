package be.ap.eaict.geocapture;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;
import be.ap.eaict.geocapture.Model.Team;
import cz.msebera.android.httpclient.Header;

public class GameRepository implements IGameRepository{

    private static IGameRepository repo = null;

    private static IGameRepository getInstance() {
        if (repo == null)
        {
            repo = new GameRepository();
        }
        return repo;
    }

    public static String userName;
    private int userKey; // api should return a key it should use to identify the user or sth
    private static int team;
    private static int lobbyId;
    public static List<Regio> regios = new ArrayList<>();

    @Override
    public void getRegios() {
        //API CALL
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://webapplication520181127093524.azurewebsites.net/api/Regio/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");

                    Gson gson = new Gson();
                    regios = gson.fromJson(str, new TypeToken<List<Regio>>() {}.getType());


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });

    }

    static public boolean JoinGame(String username, int intTeam, int intLobbyId)
    {

        //try to join game with information:  txtTeam, txtLobbyId, txtName is valid by doing API CALL
        //if returns false, game can't be started, API call should return the error information (game doesn't exist, name already in use, can't join team xx....)

        if(true)//api call returns true or false (.../api/game/join/id)
        {
            userName = username;
            team = intTeam;
            lobbyId = intLobbyId;
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


    static public void createGame(Regio regio, List<Locatie> enabledlocaties) {
        Game game = getGame();
        game = new Game(regio, game.getStarttijd(), game.Teams, enabledlocaties);
        //API CALL to create game in backend

        //API PUT game (.../api/game/id)

        JoinGame(userName,0,lobbyId); // host joins team 0 by default
    }


    static public Game getGame() {

        return null;
    }
}