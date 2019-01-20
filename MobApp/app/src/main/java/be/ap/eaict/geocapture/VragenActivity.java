package be.ap.eaict.geocapture;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import be.ap.eaict.geocapture.Model.Puzzel;
import be.ap.eaict.geocapture.Model.User;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class VragenActivity extends AppCompatActivity {


    int Vraagnummer = 0;
    GameService _gameservice = new GameService();

    @Override
    protected void onStart()
    {
        super.onStart();
        _gameservice.puzzelactive = true;
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        _gameservice.puzzelactive = false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vragen_oplossen);


        final List<Puzzel> puzzels = _gameservice.puzzels;

        final TextView vraag = (TextView) findViewById(R.id.vraag);
        final EditText antwoord = (EditText) findViewById(R.id.antwoord);
        Button next = (Button) findViewById(R.id.btnNext);

        vraag.setText(puzzels.get(0).vraag);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                puzzels.get(Vraagnummer).antwoord = antwoord.getText().toString();
                antwoord.setText("");

                if (Vraagnummer+1<puzzels.size()){
                    Vraagnummer +=1;
                    vraag.setText(puzzels.get(Vraagnummer).vraag);
                }
                else{
                    //antwoorden doorsturen

                    Gson g = new Gson();
                    String jsonString = g.toJson(puzzels);

                    StringEntity entity = null;
                    try {
                        entity = new StringEntity(jsonString);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                    // stuur api call die user in team in game toevoegd
                    SyncAPICall.post("Game/capturelocatie/"+Integer.toString(_gameservice.lobbyId)+"/"+Integer.toString(_gameservice.team+1)+"/"+Integer.toString(_gameservice.locationid), entity, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess (int statusCode, Header[] headers, byte[] res ) {
                            // called when response HTTP status is "200 OK"
                            try {
                                String str = new String(res, "UTF-8");
                                Gson gson = new Gson();
                                String response = gson.fromJson(str, new TypeToken<String>() {}.getType());

                                Toast.makeText(VragenActivity.this, response, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Toast.makeText(VragenActivity.this, "an error has occured!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            }
        });


    }
}