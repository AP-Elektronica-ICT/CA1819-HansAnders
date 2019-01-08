package be.ap.eaict.geocapture;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Puzzel;


public class VragenActivity extends AppCompatActivity {


    List<String> antwoorden = new ArrayList<String>();
    int Vraagnummer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vragen_oplossen);


        final List<Puzzel> puzzels= new ArrayList<Puzzel>();
        for(int i=0; i<10; i+=1){
            Puzzel p1 = new Puzzel("hoeveel weegt een appel" + i, "150g");
            puzzels.add(p1);
        }

        final TextView vraag = (TextView) findViewById(R.id.vraag);
        final EditText antwoord = (EditText) findViewById(R.id.antwoord);
        Button next = (Button) findViewById(R.id.btnNext);

        vraag.setText(puzzels.get(0).vraag);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                antwoorden.add(antwoord.getText().toString());
                if (Vraagnummer<puzzels.size()){
                    vraag.setText(puzzels.get(Vraagnummer).vraag);
                }
                else{
                    //antwoorden doorsturen
                    finish();
                }
                Vraagnummer +=1;
            }
        });


    }
}