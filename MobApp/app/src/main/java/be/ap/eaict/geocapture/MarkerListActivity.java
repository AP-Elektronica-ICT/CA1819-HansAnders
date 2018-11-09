package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MarkerListActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";



    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate_markers);

        List<String> markers = new ArrayList<>();
        markers.add("test");
        markers.add("Antwerpen");
        markers.add("Gent");

        final ListView markerList = (ListView) findViewById(R.id.markers_list);
        final MarkerAdapter markerAdapter = new MarkerAdapter(this, markers);
        markerList.setAdapter(markerAdapter);
    }
}
