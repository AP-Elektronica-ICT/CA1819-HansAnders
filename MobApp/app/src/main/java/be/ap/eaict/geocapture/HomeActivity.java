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
                final GameRepository gameRepository = new GameRepository();
                gameRepository.startGame(Teams);

                Intent i = new Intent(HomeActivity.this, HostConfigActivity.class);
                //i.putExtra("teams", Teams);
                i.putExtra("name", findViewById(R.id.txtName).toString());
                startActivity(i);
            }
        });

        Button btnJoinGame = (Button) findViewById(R.id.btnNewGame);
        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GameRepository gameRepository = new GameRepository();
                boolean canjoin = gameRepository.JoinGame(
                        findViewById(R.id.txtName).toString(),
                        Integer.parseInt(findViewById(R.id.txtTeam).toString()),
                        Integer.parseInt(findViewById(R.id.txtLobbyId).toString()));

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
}
