package nyc.tanjim.mathtank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Scores extends AppCompatActivity {

    private TextView aScore, tScore, qScore;
    private TextView advancedScore, quickMathScore, timeTrialsScore;
    private TextView timesPlayed, timeTaken, aTimeTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        aScore = findViewById(R.id.aScore);
        tScore = findViewById(R.id.tScore);
        qScore = findViewById(R.id.qScore);
        advancedScore = findViewById(R.id.advancedScore);
        quickMathScore = findViewById(R.id.quickMathsScore);
        timeTrialsScore = findViewById(R.id.timeTrialsScore);
        timesPlayed = findViewById(R.id.timesPlayed);
        timeTaken = findViewById(R.id.timeTaken);
        aTimeTaken = findViewById(R.id.aTimeTaken);
        SharedPreferences preferences = getSharedPreferences("highScore",MODE_PRIVATE);
        int quickTimeHighScore = preferences.getInt("quickMathHighScore",0);
        int quickTimeHighScoreTotal = preferences.getInt("quickMathHighScoreWrong",0);
        int timesPlayedText = preferences.getInt("timesPlayed",0);
        qScore.setText(String.format("High Score: %d of %d",quickTimeHighScore,quickTimeHighScoreTotal));
        timesPlayed.setText("Times played: " + Integer.toString(timesPlayedText));
    }

}
