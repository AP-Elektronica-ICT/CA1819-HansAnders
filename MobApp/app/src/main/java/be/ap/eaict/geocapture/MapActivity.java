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

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Puzzel;
import be.ap.eaict.geocapture.Model.Team;
import be.ap.eaict.geocapture.Model.User;

public class MapActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
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

        List<User> users = _gameService.game.teams.get(GameService.team-1).users;


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
        if (mMap != null) {
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
                List<User> users = _gameService.game.teams.get(GameService.team-1).users;
                int i = 0;
                for (Marker marker : teamMarkers)
                {
                    if(users.get(i).id != _gameService.userId) {
                        marker.setPosition(new LatLng(users.get(i).lat, users.get(i).lng));
                    }
                    i++;
                }
                while(i<users.size())//extra user joined
                {
                    Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.green_dot)).getBitmap();
                    Bitmap marker = Bitmap.createScaledBitmap(b, 30, 30, false);

                    MarkerOptions a = new MarkerOptions()
                            .position(new LatLng(users.get(i).lat, users.get(i).lng))
                            .alpha(0.7f)
                            .icon(BitmapDescriptorFactory.fromBitmap(marker));
                    Marker m = mMap.addMarker(a);
                    teamMarkers.add(m);
                    i++;
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
                            for(Locatie locatie : team.capturedLocaties)
                                 locatieMarkers.get(locatie.id).setIcon(BitmapDescriptorFactory.fromBitmap(marker));
                        }
                        else
                        {
                            Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.enemycapture_dot)).getBitmap();
                            Bitmap marker = Bitmap.createScaledBitmap(b, 40, 40, false);
                            for(Locatie locatie : team.capturedLocaties)
                                locatieMarkers.get(locatie.id).setIcon(BitmapDescriptorFactory.fromBitmap(marker));
                        }
                    }
                }
                //update eersteplek
                bestTeam();

                Locatie l = canCapture();
                if(l != null && !_gameService.puzzelactive)
                {
                    Intent intent = new Intent(MapActivity.this , VragenActivity.class);
                    _gameService.puzzels = new ArrayList<>();
                    for(final Puzzel puzzel : l.puzzels)
                        _gameService.puzzels.add(new Puzzel(puzzel.id,puzzel.vraag, null));
                    _gameService.locationid = l.id;
                    startActivity(intent);
                }
            }

            public void onFinish() {

            }

        }.start();
    }


    private void initializeGameTime(){
        int tijd = _gameService.game.getRegio().getTijd()*60 /* - (huidige tijd - starttijd) */  ;
        new CountDownTimer(tijd, 1000) {
            public void onTick(long millisUntilFinished) {
                String timer = String.format(Locale.getDefault(), "%02d:%02d:%02d remaining",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                gameTime.setText(timer);
                //Toast.makeText(MapActivity.this, timer, Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                gameTime.setText("einde");
            }

        }.start();
    }

    public void bestTeam(){
        List<Team> teams = _gameService.game.teams;
        int bestteamid = 999;
        int bestscore = 0;

        for(Team team : teams) {
            int score = team.getCapturedLocaties().size();
            if (score > bestscore){
                bestteamid = team.id;
            }
        }
        if (bestteamid == 99){
            bestTeamTxt.setText("Team: -");
        }else {
            bestTeamTxt.setText("Team: " + String.valueOf(bestteamid));
        }
    }

    private Locatie canCapture(){
        List<Locatie> locaties = _gameService.game.getEnabledLocaties();
        if(_locatie != null)
            for (int i = 0; i < locaties.size(); i++) {
                double x = _locatie.getLongitude() - locaties.get(i).lng;
                double y = _locatie.getLatitude() - locaties.get(i).lat;

                x = x * x;
                y = y * y;

                double afstand = Math.sqrt(x+y);
                //afstand = 0.000001; // fake capture testing thingy
                if (afstand < 0.00026949458){
                    return(locaties.get(i));
                }
            }
        return null;
    }
}
