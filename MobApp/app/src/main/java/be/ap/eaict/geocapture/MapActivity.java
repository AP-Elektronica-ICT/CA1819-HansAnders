package be.ap.eaict.geocapture;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
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

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import be.ap.eaict.geocapture.Model.Locatie;

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gameTime = (TextView) findViewById(R.id.gametime);
        initializeGameTime();
        keepGameUpToDate();




    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        List<Locatie> locaties = _gameService.game.getEnabledLocaties();

        LatLng center = new LatLng(0, 0);
        for(Locatie locatie:locaties){
            LatLng latLng = new LatLng(locatie.getLat(), locatie.getLng());
            center = new LatLng(center.latitude + locatie.getLat(),center.longitude + locatie.getLng());
            googleMap.addMarker(new MarkerOptions().position(latLng)
                    .title(locatie.getLocatienaam()));
        }





        Bitmap b =((BitmapDrawable)getResources().getDrawable(R.drawable.green_dot)).getBitmap();
        Bitmap marker = Bitmap.createScaledBitmap(b, 30, 30, false);

        MarkerOptions a = new MarkerOptions()
                .position(new LatLng(50,6))
                .alpha(0.7f)
                .icon(BitmapDescriptorFactory.fromBitmap(marker));
        Marker m = googleMap.addMarker(a);
        
        //update location
        m.setPosition(new LatLng(50,7));




        if(locaties.size()>0)
            center = new LatLng(center.latitude/locaties.size(), center.longitude/locaties.size());


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center,14));

        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.d(TAG, "onMyLocationChange: lat: "+ location.getLatitude());
                Log.d(TAG, "onMyLocationChange: lng: "+location.getLongitude());
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
        } else if (mMap != null) {
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
        new CountDownTimer(_gameService.game.getRegio().getTijd()*60, 1000) {

            public void onTick(long millisUntilFinished) {
                //getgame
                _gameService.getGame(_gameService.game.id);
            }

            public void onFinish() {
            }

        }.start();
    }
    private void initializeGameTime(){
        new CountDownTimer(_gameService.game.getRegio().getTijd()*60, 1000) {

            public void onTick(long millisUntilFinished) {
                String timer = String.format(Locale.getDefault(), "Time Remaining %02d hours: %02d minutes, %02d seconds",
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
}
