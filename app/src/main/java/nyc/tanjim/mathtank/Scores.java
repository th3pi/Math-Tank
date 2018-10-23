package nyc.tanjim.mathtank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Scores extends AppCompatActivity {

    private TextView aScore, tScore, qScore;
    private TextView advancedScore, quickMathScore, timeTrialsScore;
    private TextView timesPlayed, timeTaken, aTimeTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);


        aScore = findViewById(R.id.aScore);
        tScore = findViewById(R.id.tScore);
        qScore = findViewById(R.id.qScore);
        advancedScore = findViewById(R.id.advancedScore);
        quickMathScore = findViewById(R.id.quickMathHigh);
        timeTrialsScore = findViewById(R.id.timeTrialsScore);
        timesPlayed = findViewById(R.id.timesPlayed);
        timeTaken = findViewById(R.id.timeTaken);
        aTimeTaken = findViewById(R.id.aTimeTaken);
        SharedPreferences preferences = getSharedPreferences("highScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int quickTimeHighScore = preferences.getInt("quickMathHighScore",0);
        int quickTimeHighScoreTotal = preferences.getInt("quickMathHighScoreWrong",0);
        int timesPlayedText = preferences.getInt("timesPlayed",0);
        int timeTrialsHighScore = preferences.getInt("timeTrialsHighScore",0);
        int advancedHighScore = preferences.getInt("advancedHighScore",0);
        int advancedHighScoreTotal = preferences.getInt("advancedHighScoreTotal",0);
        int advancedTimeTaken = preferences.getInt("madvancedTimeTaken",1200);
        int timeTrialsTimeTaken = preferences.getInt("tTimeTaken",1200);
        qScore.setText(getString(R.string.qScore,quickTimeHighScore,quickTimeHighScoreTotal));
        timesPlayed.setText(getString(R.string.timesPlayed,timesPlayedText));
        tScore.setText(getString(R.string.tScore,timeTrialsHighScore));
        timeTaken.setText(getString(R.string.timeTaken,timeTrialsTimeTaken));
        aScore.setText(getString(R.string.aScore,advancedHighScore,advancedHighScoreTotal));
        aTimeTaken.setText(getString(R.string.aTimeTaken,advancedTimeTaken));

        if(darkModePref){
            ScrollView scrollView = (findViewById(R.id.scoresBg));
            scrollView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.qboard_black));
            qScore.setBackground(getDrawable(R.drawable.main_menu_button_bg_dk));
            quickMathScore.setBackground(getDrawable(R.drawable.main_menu_button_bg_dk));
            timesPlayed.setBackground(getDrawable(R.drawable.main_menu_button_bg_dk));
            tScore.setBackground(getDrawable(R.drawable.main_menu_button_og_dk));
            timeTrialsScore.setBackground(getDrawable(R.drawable.main_menu_button_og_dk));
            timeTaken.setBackground(getDrawable(R.drawable.main_menu_button_og_dk));
            aScore.setBackground(getDrawable(R.drawable.main_menu_button_rd_dk));
            advancedScore.setBackground(getDrawable(R.drawable.main_menu_button_rd_dk));
            aTimeTaken.setBackground(getDrawable(R.drawable.main_menu_button_rd_dk));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
        }

        if(timeTrialsTimeTaken == 1200){
            timeTaken.setText(getString(R.string.timeTaken,0));
        }
        if(advancedTimeTaken == 1200){
            aTimeTaken.setText(getString(R.string.aTimeTaken,0));
        }

        touchToReset(quickMathScore,editor,quickMathScore,"quickMathDifference","quickMathHighScore","quickMathHighScoreWrong", "Quick Math Stats");
        touchToReset(advancedScore,editor,advancedScore,"advancedDifference","advancedHighScore","advancedHighScoreTotal", "Advanced Stats");
        touchToReset(timeTrialsScore,editor,timeTrialsScore,null,"timeTrialsHighScore",null, "Timetrials Stats");
    }

    private void touchToReset(TextView viewToBeListened, final SharedPreferences.Editor edit, final TextView title, final String difference, final String highScore, final String total, final String titleText){
        viewToBeListened.setOnClickListener(new View.OnClickListener() {
            int touchFive = 5;
            @Override
            public void onClick(View v) {
                --touchFive;
                if(touchFive >= 0) {
                    title.setText("Tap " + Integer.toString(touchFive) + " more time to reset");
                }
                if(touchFive == 0){
                    if(titleText.equals("Advanced Stats")){
                        edit.putInt("madvancedTimeTaken",1200).apply();
                    }else if(titleText.equals("Timetrials Stats")){
                        edit.putInt("tTimeTaken",1200).apply();
                        edit.putFloat("hiddenElo",0).apply();
                    }
                    if(!titleText.equals("Timetrials Stats")) {
                        edit.putInt(difference, 100).apply();
                        edit.putInt(total,0).apply();
                    }
                    edit.putInt(highScore,0).apply();
                    title.setText(titleText);
                    Toasty.success(Scores.this,"Score reset successful",Toast.LENGTH_SHORT,true).show();
                    recreate();
                }
            }
        });
    }

    public void openScoredHow(View view){
        startActivity(new Intent(this,scoredHow.class));
    }

}
