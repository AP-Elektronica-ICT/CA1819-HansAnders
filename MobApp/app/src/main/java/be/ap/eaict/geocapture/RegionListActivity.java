package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RegionListActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";



    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        List<String> markers = new ArrayList<>();
        markers.add("r1");
        markers.add("r2");
        markers.add("r3");

        final ListView markerList = (ListView) findViewById(R.id.region_list);
        final MarkerAdapter markerAdapter = new MarkerAdapter(this, markers);
        markerList.setAdapter(markerAdapter);
    }
}
