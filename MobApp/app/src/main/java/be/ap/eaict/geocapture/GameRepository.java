package be.ap.eaict.geocapture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;
import com.loopj.android.http.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;
import be.ap.eaict.geocapture.Model.Team;
import be.ap.eaict.geocapture.Model.User;
import cz.msebera.android.httpclient.Header;

public class GameRepository extends AppCompatActivity implements IGameRepository {

    private static IGameRepository repo = null;

    private static IGameRepository getInstance() {
        if (repo == null)
        {
            repo = new GameRepository();
        }
        return repo;
    }

    public static String userName;
    private static int userKey; // api should return a key it should use to identify the user or sth
    private static int team;
    private static int lobbyId;
    public static List<Regio> regios = new ArrayList<>();


    @Override
    public void getRegios() {
        //API CALL
        SyncAPICall.get("Regio/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");

                    Gson gson = new Gson();
                    regios = gson.fromJson(str, new TypeToken<List<Regio>>() {}.getType());
                    Log.d("tag", "onSuccess: "+regios);
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

    public boolean JoinGame(final String username, final int intTeam, final int intLobbyId, final AppCompatActivity homeActivity)
    {

        //try to join game with information:  txtTeam, txtLobbyId, txtName is valid by doing API CALL
        //if returns false, game can't be started, API call should return the error information (game doesn't exist, name already in use, can't join team xx....)
        final User user = new User(username, 4,6);
        RequestParams params = new RequestParams();
        params.put("user", user);
        params.put("team", intTeam);


        SyncAPICall.post("Game/join/"+Integer.toString(intLobbyId), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");
                    Gson gson = new Gson();

                    userName = username;
                    team = intTeam;
                    lobbyId = intLobbyId;

                    Intent i = new Intent(homeActivity , MapActivity.class);
                    startActivity(i);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });

        return false;
    }

    public void startGame(int teams, final String name, final HomeActivity homeActivity)//new lobby that people can join
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
        RequestParams params = new RequestParams();
        params.put("game", startgame);

        SyncAPICall.post("Game/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");
                    Gson gson = new Gson();
                    game = gson.fromJson(str, new TypeToken<Game>() {}.getType());
                    lobbyId = game.ID;

                    Intent i = new Intent(homeActivity , HostConfigActivity.class);
                    i.putExtra("name", name);
                    startActivity(i);

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

    public void createGame(Regio regio, List<Locatie> enabledlocaties, final HostConfigActivity hostConfigActivity) {
        game = new Game(regio, game.getStarttijd(), game.Teams, enabledlocaties);
        //API CALL to create game in backend

        //API PUT game (.../api/game/id)
        RequestParams params = new RequestParams();
        params.put("game", game);

        SyncAPICall.put("Game/"+Integer.toString(lobbyId), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                (new GameRepository()).JoinGame(userName,0,lobbyId, hostConfigActivity); // host joins team 0 by default
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });

    }

    public static Game game;
    static public void getGame(int lobbyId) {

        SyncAPICall.get("Game/"+Integer.toString(lobbyId), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");

                    Gson gson = new Gson();
                    game = gson.fromJson(str, new TypeToken<Game>() {}.getType());

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
}