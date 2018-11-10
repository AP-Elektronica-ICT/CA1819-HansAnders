package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import be.ap.eaict.geocapture.Model.Locatie;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        DummyRepositoryRegios dummyRepositoryRegios = new DummyRepositoryRegios();
        List<Locatie> locaties = dummyRepositoryRegios.getGame().getEnabledLocaties();

        for(Locatie locatie:locaties){
            LatLng latLng = new LatLng(locatie.getLng(), locatie.getLat());
            googleMap.addMarker(new MarkerOptions().position(latLng)
                    .title(locatie.getLocatienaam()));
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locaties.get(0).getLng(), locaties.get(0).getLat()),14));
    }
}
