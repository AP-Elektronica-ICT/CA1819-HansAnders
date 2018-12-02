package be.ap.eaict.geocapture;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Regio;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (IsServicesOK()){
            init();
        }
        HttpCall();
        (new GameRepository()).getRegios();
    }

    private  void init(){
        //create new game:
        Button btnHostConfig = (Button) findViewById(R.id.btnHostGame);
        btnHostConfig.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                EditText teams = (EditText) findViewById(R.id.txtHostTeams);
                int Teams = 0;
                if(teams.getText().length() > 0)
                    Teams = Integer.parseInt(teams.getText().toString());

                //start game instance in backend so that lobbyid is created and people can join
                (new GameRepository()).startGame(Teams, findViewById(R.id.txtName).toString(), HomeActivity.this);

            }
        });

        Button btnJoinGame = (Button) findViewById(R.id.btnJoinGame);
        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GameRepository gameRepository = new GameRepository();
                gameRepository.JoinGame(
                        findViewById(R.id.txtName).toString(),
                        Integer.parseInt(findViewById(R.id.txtTeam).toString()),
                        Integer.parseInt(findViewById(R.id.txtLobbyId).toString()), HomeActivity.this);

                Intent mapintent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(mapintent);
            }
        });
    }
    public boolean IsServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HomeActivity.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void HttpCall(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://webapplication520181127093524.azurewebsites.net/api/Regio/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"
                Log.d(TAG, "onSuccess: api call success");
                try {
                    String str = new String(res, "UTF-8");

                    Gson gson = new Gson();
                    List<Regio> recipesList = gson.fromJson(str, new TypeToken<List<Regio>>() {}.getType());

                    Log.d(TAG, "onSuccess: fromclasslist: "+recipesList.get(0).getLocaties().get(0).getLat());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d(TAG, "onFailure: api call failure");
            }
        });
    }

}
