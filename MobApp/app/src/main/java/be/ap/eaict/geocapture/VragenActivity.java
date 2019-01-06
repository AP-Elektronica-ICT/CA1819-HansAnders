package be.ap.eaict.geocapture;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Puzzel;


public class VragenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vragen_oplossen);


        List<Puzzel> puzzels= new ArrayList<Puzzel>();
        for(int i=0; i<10; i+=1){
            Puzzel p1 = new Puzzel("hoeveel weegt een appel", "150g");
            puzzels.add(p1);
        }

        final ListView vragenList = (ListView) findViewById(R.id.vragen_list);
        final VragenAdapter vragenAdapter = new VragenAdapter(this, puzzels);
        vragenList.setAdapter(vragenAdapter);
    }
}