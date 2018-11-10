package be.ap.eaict.geocapture;

import java.util.List;

import be.ap.eaict.geocapture.Model.Game;
import be.ap.eaict.geocapture.Model.Regio;

public interface IDummyRepositoryRegios {
    List<Regio> getRegios();
    Game getGame();
    void createGame(Game game);
}
