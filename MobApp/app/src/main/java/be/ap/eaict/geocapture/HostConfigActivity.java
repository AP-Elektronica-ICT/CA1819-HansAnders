package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HostConfigActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_config);

        List<String> markers = new ArrayList<>();
        markers.add("test");
        markers.add("Antwerpen");
        markers.add("Gent");

        final ListView markerList = (ListView) findViewById(R.id.markers_list);
        final MarkerAdapter markerAdapter = new MarkerAdapter(this, markers);
        markerList.setAdapter(markerAdapter);




        List<String> region = new ArrayList<>();
        region.add("r1");
        region.add("r2");
        region.add("r3");

        final ListView regionList = (ListView) findViewById(R.id.region_list);
        final MarkerAdapter regionAdapter = new MarkerAdapter(this, region);
        regionList.setAdapter(regionAdapter);
    }
}
