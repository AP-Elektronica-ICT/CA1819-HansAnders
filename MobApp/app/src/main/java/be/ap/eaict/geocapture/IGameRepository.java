package be.ap.eaict.geocapture;

import org.json.JSONException;

import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;

public interface IGameRepository {
    void getRegios() throws JSONException;
}
