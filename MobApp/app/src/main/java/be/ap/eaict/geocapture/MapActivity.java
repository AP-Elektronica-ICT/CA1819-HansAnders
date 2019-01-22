package be.ap.eaict.geocapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import be.ap.eaict.geocapture.Model.CaptureLocatie;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Puzzel;
import be.ap.eaict.geocapture.Model.Team;
import be.ap.eaict.geocapture.Model.User;
import cz.msebera.android.httpclient.Header;

public class MapActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    GameService _gameservice = new GameService();
    private static final String TAG = "MapActivity";

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private GameService _gameService = new GameService();
    TextView gameTime;
    TextView bestTeamTxt;
    private Location _locatie;

    HashMap<Integer, Marker> locatieMarkers = new HashMap<Integer, Marker>() {};
    List<Marker> teamMarkers = new ArrayList<Marker>() {};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gameTime = (TextView) findViewById(R.id.gametime);
        bestTeamTxt = (TextView) findViewById(R.id.bestTeamTxt);
        initializeGameTime();
        keepGameUpToDate();

    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        List<Locatie> locaties = _gameService.game.getEnabledLocaties();


        float centerlat = 0;
        float centerlng = 0;
        LatLng center = new LatLng(0, 0);
        Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.grey_dot)).getBitmap();
        Bitmap greymarker = Bitmap.createScaledBitmap(b, 40, 40, false);

        for(Locatie locatie:locaties){
            LatLng latLng = new LatLng(locatie.getLat(), locatie.getLng());
            centerlat = centerlat + locatie.getLat();
            centerlng = centerlng + locatie.getLng();
            //center = new LatLng(center.latitude + locatie.getLat(),center.longitude + locatie.getLng());
            locatieMarkers.put(
                locatie.id,
                googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .alpha(0.7f)
                    .icon(BitmapDescriptorFactory.fromBitmap(greymarker))
                )
            );
        }

        if(locaties.size()>0)
            center = new LatLng(centerlat/locaties.size(), centerlng/locaties.size());




        b =((BitmapDrawable)getResources().getDrawable(R.drawable.green_dot)).getBitmap();
        Bitmap marker = Bitmap.createScaledBitmap(b, 27, 27, false);

        List<User> users = _gameService.game.teams.get(GameService.team).users;


        Log.d(TAG, "onMapReady: "  + users);
        for(User lid:users){
            if(lid.id != _gameService.userId)
            {
                MarkerOptions a = new MarkerOptions()
                        .position(new LatLng(lid.lat, lid.lng))
                        .alpha(0.7f)
                        .icon(BitmapDescriptorFactory.fromBitmap(marker));
                Marker m = googleMap.addMarker(a);
                teamMarkers.add(m);
            }
        }
        /*  //testmarker:
        MarkerOptions a = new MarkerOptions()
                .position(new LatLng(5,5))
                .alpha(0.7f)
                .icon(BitmapDescriptorFactory.fromBitmap(marker));
        Marker m = googleMap.addMarker(a);*/

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center,14));

        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                _locatie = location;
            }
        });
    }


    @Override
    protected void onDestroy() {
        SyncAPICall.delete("Game/deleteplayerlocatie/"+Integer.toString(_gameservice.lobbyId)+"/"+Integer.toString(_gameservice.team)+"/"+ Integer.toString(_gameservice.userId), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                // called when response HTTP status is "200 OK"

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
        });
        super.onDestroy();
    }



    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
        Log.d(TAG, "onabcxyz lng: "+ location.getLongitude() + " lat: " + location.getLatitude());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");

    }

    private void keepGameUpToDate() {
        new CountDownTimer(_gameService.game.getRegio().getTijd()*60, 2000) {

            public void onTick(long millisUntilFinished) {
                //update player locatie, returns game
                if(_locatie != null)
                    _gameService.UpdatePlayerLocatie(new LatLng(_locatie.getLatitude(), _locatie.getLongitude()));

                //update other players locaties
                for (Marker marker : teamMarkers)
                {
                    marker.remove();
                }
                List<User> users = _gameService.game.teams.get(GameService.team).users;
                for(User user : users)
                    if(user.id != _gameService.userId)
                    {
                        Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.green_dot)).getBitmap();
                        Bitmap marker = Bitmap.createScaledBitmap(b, 30, 30, false);

                        teamMarkers.add(mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(user.lat, user.lng))
                                .alpha(0.7f)
                                .icon(BitmapDescriptorFactory.fromBitmap(marker))));
                    }

                //update locatie kleuren:
                for(Team team : _gameService.game.teams)
                {
                    if(team.capturedLocaties.size()>0)
                    {
                        if(team.id == _gameService.game.teams.get(_gameService.team).id)
                        {
                            Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.captured_dot)).getBitmap();
                            Bitmap marker = Bitmap.createScaledBitmap(b, 40, 40, false);
                            for(CaptureLocatie captureLocatie : team.capturedLocaties)
                                 locatieMarkers.get(captureLocatie.locatie.id).setIcon(BitmapDescriptorFactory.fromBitmap(marker));
                        }
                        else
                        {
                            Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.enemycapture_dot)).getBitmap();
                            Bitmap marker = Bitmap.createScaledBitmap(b, 40, 40, false);
                            for(CaptureLocatie captureLocatie : team.capturedLocaties)
                                locatieMarkers.get(captureLocatie.locatie.id).setIcon(BitmapDescriptorFactory.fromBitmap(marker));
                        }
                    }
                }

                bestTeam();

                int l = canCapture();
                if(l != 0 && !_gameService.puzzelactive)
                {
                    Intent intent = new Intent(MapActivity.this , VragenActivity.class);
                    _gameService.puzzels = new ArrayList<>();
                    for(Team team : _gameService.game.teams)
                        for(CaptureLocatie captureLocatie : team.capturedLocaties)
                            if(captureLocatie.locatie.id == l )
                            {
                                int maxpoints = 0;
                                for(Puzzel puzzel : captureLocatie.locatie.puzzels)
                                    maxpoints+= puzzel.points;
                                Toast.makeText(MapActivity.this, "CaptureStrength: "+ captureLocatie.score+ "/"+maxpoints, Toast.LENGTH_SHORT).show();
                                l = 0; // kill capture 'ability'
                            }
                    for(final Locatie locatie : _gameService.game.regio.locaties)
                        if(locatie.id == l)
                        {
                            for(Puzzel puzzel : locatie.puzzels)
                                _gameService.puzzels.add(new Puzzel(puzzel.id,puzzel.vraag, null));
                            _gameService.locationid = l;
                            startActivity(intent);
                        }
                }
            }

            public void onFinish() {
                Intent Leaderboard = new Intent(MapActivity.this, LeaderboardActivity.class);
                startActivity(Leaderboard);
            }

        }.start();
    }


    //gametime wordt via de app met internet tijd geregeld zodat iedereen gelijk loopt.
    private void initializeGameTime(){
        int tijd = _gameService.game.getRegio().getTijd()*60 - (int)(System.currentTimeMillis() -   _gameService.game.starttijd);
        new CountDownTimer(tijd, 1000) {
            public void onTick(long millisUntilFinished) {
                String timer = String.format(Locale.getDefault(), "%02d:%02d:%02d remaining",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                gameTime.setText(timer);
            }

            public void onFinish() {

                gameTime.setText("einde game");
            }

        }.start();
    }

    public void bestTeam(){
        List<Team> teams = _gameService.game.teams;
        int bestteamid = 999;
        int bestscore = 0;

        //checken of er score zijn gemaakt om het beste team te updaten.
        for(Team team : teams) {
            int score = 0;
            for(CaptureLocatie loc :  team.getCapturedLocaties())
                score += loc.score;
            if (score > bestscore){
                bestscore = score;
                bestteamid = team.id;
            }
        }

        if (bestteamid == 999){
            bestTeamTxt.setText("Best Team: -" );
        }else {
            bestTeamTxt.setText("Best Team: " + String.valueOf(bestteamid) + " score: " + bestscore);
        }
        int score = 0;
        for(CaptureLocatie loc :  teams.get(_gameService.team).capturedLocaties )
            score += loc.score;
        bestTeamTxt.setText(bestTeamTxt.getText() + "\nMy teamId: "+ teams.get(_gameService.team).id + " score: " + score);
    }

    private int canCapture(){
        List<Locatie> locaties = _gameService.game.getEnabledLocaties();
        List<Locatie> capturedlocaties = new ArrayList<>();
        for(CaptureLocatie captureLocatie :  _gameService.game.teams.get(_gameService.team).capturedLocaties)
            capturedlocaties.add(captureLocatie.locatie);

        //controleren of je binnen een straal van 30m bent om te kunnen capturen.
        if(_locatie != null)
            for (int i = 0; i < locaties.size(); i++) {
                double x = _locatie.getLongitude() - locaties.get(i).lng;
                double y = _locatie.getLatitude() - locaties.get(i).lat;

                x = x * x;
                y = y * y;

                double afstand = Math.sqrt(x+y);

                //0.00026949458 is 30m in graden op de wereld
                if (afstand < 0.00026949458){
                    boolean contains = false;
                    for(Locatie ll : capturedlocaties)
                    {
                        if(ll.id == locaties.get(i).id)
                            contains = true;
                    }
                    if(contains == false)
                    {
                        int send = locaties.get(i).id;
                        return send;
                    }
                }
            }
        return 0;
    }
}
