package be.ap.eaict.geocapture;

import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Regio;

public interface IGameRepository {
    List<Regio> getRegios();
    Game getGame();
    void createGame(Regio regio, List<Locatie> enabledlocaties, String userName);
}
