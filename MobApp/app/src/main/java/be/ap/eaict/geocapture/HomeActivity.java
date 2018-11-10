package be.ap.eaict.geocapture;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (IsServicesOK()){
            init();
        }
    }

    private  void init(){
        Button btnHostConfig = (Button) findViewById(R.id.btnHost);
        btnHostConfig.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(HomeActivity.this, HostConfigActivity.class));
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(HomeActivity.this, MapActivity.class));
            }
        });

        Button btnMarkersList = (Button) findViewById(R.id.btnList);
        btnMarkersList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(HomeActivity.this, MarkerListActivity.class));
            }
        });

        Button btnlocationsList = (Button) findViewById(R.id.btnRegion);
        btnlocationsList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(HomeActivity.this, RegionListActivity.class));
            }
        });
    }
    public boolean IsServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HomeActivity.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
