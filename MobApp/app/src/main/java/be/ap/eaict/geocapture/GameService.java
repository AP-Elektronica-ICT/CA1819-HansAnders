package be.ap.eaict.geocapture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class GameService extends AppCompatActivity implements IGameRepository {

    private static IGameRepository repo = null;

    private static IGameRepository getInstance() {
        if (repo == null)
        {
            repo = new GameService();
        }
        return repo;
    }

    public static String userName;
    private static int userKey; // api should return a key it should use to identify the user or sth
    private static int team;
    public static int lobbyId;
    public static List<Regio> regios = new ArrayList<>();


    @Override
    public void getRegios() { // steekt alle regio's in de variabele 'regio's' zodat deze kunnen gebruikt worden door hostconfigactivity
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
        //maak user aan en steek het in een json entity
        final User user = new User(username, 4,6);
//        RequestParams params = new RequestParams();
//        params.put("user", user);
//        params.put("team", intTeam);

        Gson g = new Gson();
        String jsonString = g.toJson(user);

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        Log.d(" ", "JoinGame: ");
        Log.d(" ", "JoinGame: " + "Game/join/"+Integer.toString(intLobbyId)+"/"+Integer.toString(intTeam));
        // stuur api call die user in team in game toevoegd
        SyncAPICall.post("Game/join/"+Integer.toString(intLobbyId)+"/"+Integer.toString(intTeam), entity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                Log.d(" ", "onSuccess: game joined" );
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");
                    Gson gson = new Gson();
                    //game = gson.fromJson(str, new TypeToken<Game>() {}.getType());

                    userName = username;
                    team = intTeam;
                    lobbyId = intLobbyId;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
        getGame(intLobbyId);

        return false;
    }

    public void CreateGame(int teams, final String name, final Intent intent)//new lobby that people can join
    {
        List<Team> listTeams = new ArrayList<>();
        for(int i =0; i< teams; i++)
        {
           listTeams.add(new Team());//empty teams initiated
        }
        Game startgame = new Game(null,System.currentTimeMillis(), listTeams, null);
        //POST startgame
        Gson g = new Gson();
        String jsonString = g.toJson(startgame);
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


        //api call to create new game and create lobby id so people can join
        // API POST EMPTY GAME! --> WILL RETURN GAME WITH id

        //RequestParams params = new RequestParams();
        //params.put("game", jsonString);

        boolean canstart = false;
        SyncAPICall.post("Game/", entity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                try {
                    String str = new String(res, "UTF-8");
                    Gson gson = new Gson();
                    game = gson.fromJson(str, new TypeToken<Game>() {}.getType());
                    Log.d("strid", str);
                    lobbyId = game.id;

                    //intent.putExtra("name", name);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //startActivity(new Intent(GameService.this, HostConfigActivity.class));

                            //Toast.makeText(getApplicationContext(), "successfully created game", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //startActivity(intent);
                    //new Intent((Activity)GameService.this, HostConfigActivity.class)
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("fuckdis","logger2");
            }
        });
    }


    public void StartGame(Regio regio, List<Locatie> enabledlocaties, final HostConfigActivity hostConfigActivity) {
        game = new Game(regio, game.getStarttijd(), game.teams, enabledlocaties);
        //API CALL to create game in backend

        //API PUT game (.../api/game/id)

        Gson g = new Gson();
        String jsonString = g.toJson(game);
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        SyncAPICall.put("Game/"+Integer.toString(lobbyId), entity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                (new GameService()).JoinGame(userName,0,lobbyId, hostConfigActivity); // host joins team 0 by default
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
    }

    public static Game game;
    static public void getGame(int lobbyId) {//moet via socket gebeuren

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