package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
}
