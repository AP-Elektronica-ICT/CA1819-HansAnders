package be.ap.eaict.geocapture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;
import be.ap.eaict.geocapture.Model.Team;

public class HostConfigActivity extends AppCompatActivity {
    private static final String TAG = "HOSTCONFIG";

    static Regio regio;
    List<Locatie> regiolocaties = new ArrayList<Locatie>();

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_config);



        //final int teams = getIntent().getIntExtra("teams", 0);
        //final TextView TextView_teams = (TextView)findViewById(R.id.hostconfig_teams);
        //TextView_teams.setText(""+teams);

        //final DummyRepositoryRegios dummyRepositoryRegios = new DummyRepositoryRegios();
        final GameRepository gameRepository = new GameRepository();

        Log.d(TAG, "onCreate: "+gameRepository.regios);


        final ListView regiosList = (ListView) findViewById(R.id.region_list);
        final HostConfigRegioAdapter hostConfigRegioAdapter = new HostConfigRegioAdapter(this, gameRepository.regios);
        regiosList.setAdapter(hostConfigRegioAdapter);


        final ListView locationsList = (ListView) findViewById(R.id.markers_list);
        final HostConfigLocatiesAdapter locationAdapter = new HostConfigLocatiesAdapter(this, regiolocaties);
        locationsList.setAdapter(locationAdapter);

        regiosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                regio = (Regio) adapterView.getItemAtPosition(position);
                Log.d(TAG, "onItemClick: "+regio + " " + position);
                locationAdapter.clear();
                regiolocaties = regio.getLocaties();
                Log.d(TAG, "onItemClick: ");
                //regiolocaties = gameRepository.regios
                locationAdapter.addAll(regiolocaties);
            }
        });

        locationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*if( regiolocaties.get(position).used == null){
                    regiolocaties.get(position).used = false;
                }
                else {
                    regiolocaties.get(position).used = !regiolocaties.get(position).used;
                }*/
                regiolocaties.get(position).used = (regiolocaties.get(position).used==null ? false:!regiolocaties.get(position).used);
                locationAdapter.clear();
                locationAdapter.addAll(regiolocaties);
            }
        });

        TextView txtDeelnemers = (TextView) findViewById(R.id.txtDeelnemers);
        // somehow let this pull the amount of players from database and let it show here

        Button btnStart = (Button) findViewById(R.id.btnStart);
        gameRepository.userName = getIntent().getStringExtra("name");
        btnStart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //create new game
                if(regio != null)
                {
                    Log.d(TAG,"buttonclick");
                    List<Locatie> enabledLocaties = new ArrayList<>();
                    for(Locatie locatie: regiolocaties)
                        if(locatie.used==null || locatie.used==true) enabledLocaties.add(locatie);
                    Log.d(TAG,"enabledlocaties caluclated");

                    //dummyRepositoryRegios.createGame(new Game(0,regio,System.currentTimeMillis(),null,enabledLocaties));// Regio regio, int starttijd, List<Team> teams, List<Locatie> enabledLocaties)
// public void createGame(Regio regio, List<Locatie> enabledlocaties, String userName) {

                    GameRepository.createGame(regio,enabledLocaties);// Regio regio, int starttijd, List<Team> teams, List<Locatie> enabledLocaties)

                    Intent mapintent = new Intent(HostConfigActivity.this, MapActivity.class);
                    startActivity(mapintent);
                }
                else
                {
                    //error!
                    Toast.makeText(HostConfigActivity.this, "you can't make a new game", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
