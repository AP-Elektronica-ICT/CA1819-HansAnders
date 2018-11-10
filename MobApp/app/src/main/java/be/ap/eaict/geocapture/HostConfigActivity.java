package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;

public class HostConfigActivity extends AppCompatActivity {

    Game game;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_config);

        DummyRepositoryRegios dummyRepositoryRegios = new DummyRepositoryRegios();

        final ListView regiosList = (ListView) findViewById(R.id.region_list);
        final HostConfigRegioAdapter hostConfigRegioAdapter = new HostConfigRegioAdapter(this, dummyRepositoryRegios.getRegios());
        regiosList.setAdapter(hostConfigRegioAdapter);


        final ListView locationsList = (ListView) findViewById(R.id.markers_list);
        final HostConfigLocatiesAdapter locationAdapter = new HostConfigLocatiesAdapter(this, new ArrayList<Locatie>());
        locationsList.setAdapter(locationAdapter);

        regiosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Regio regio = (Regio) adapterView.getItemAtPosition(position);
                locationAdapter.clear();
                locationAdapter.addAll(regio.getLocaties());
            }
        });

        locationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.getSelectedItem();
            }
        });


    }
}
