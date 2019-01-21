package be.ap.eaict.geocapture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import be.ap.eaict.geocapture.Model.CaptureLocatie;
import be.ap.eaict.geocapture.Model.Team;


public class LeaderboardActivity extends AppCompatActivity {
    private static final String TAG = "HOSTCONFIG";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        final GameService _gameService = new GameService();

        List<Team> teams = _gameService.game.teams;
        int bestscore = 0;

        for(Team team : teams) {
            int score = 0;
            for(CaptureLocatie loc :  team.getCapturedLocaties())
                score += loc.score;
            if (score > bestscore){
                bestscore = score;
            }
        }

        int score = 0;
        for(CaptureLocatie loc :  teams.get(_gameService.team).capturedLocaties )
            score += loc.score;

        TextView besteTeam = (TextView) findViewById(R.id.txtGewonnen);
        if (score == bestscore){
            besteTeam.setText("YOU WON \n Your score is:" + score);
        }
        else {
            besteTeam.setText("You LOST the game \n your score is:" + score);
        }
    }
}
