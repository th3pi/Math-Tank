package nyc.tanjim.mathtank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import es.dmoral.toasty.Toasty;

public class TimeTrialsLoadingScreen extends AppCompatActivity {
    private static int loadingScreenTime = 4000;
    private Boolean addition, subtraction, multiplication, division, timerr, kidsmode;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trials_loading_screen);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.timetrialsbg));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.qboard_black));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
        }
        TextView hintText = findViewById(R.id.hintText);
        Random rd = new Random();
        switch (rd.nextInt(4)){
            case 0:
                hintText.setText(getString(R.string.time_resets));
                break;
            case 1:
                hintText.setText(getString(R.string.improve));
                break;
            case 2:
                hintText.setText(getString(R.string.difficulty));
                break;
            case 3:
                hintText.setText(getString(R.string.friends));
                break;
        }
        timer = findViewById(R.id.timeText);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),TimeTrials.class));
                finish();
            }
        },loadingScreenTime);
        new CountDownTimer(loadingScreenTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(getString(R.string.loading_screen_timer, (int) millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();
        addition = sharedPref.getBoolean(SettingsActivity.KEY_ADDITION_ONLY_TIMETRIALS,false);
        subtraction = sharedPref.getBoolean(SettingsActivity.KEY_SUBTRACTION_ONLY_TIMETRIALS,false);
        multiplication = sharedPref.getBoolean(SettingsActivity.KEY_MULTIPLICATION_ONLY_TIMETRIALS,false);
        division = sharedPref.getBoolean(SettingsActivity.KEY_DIVISION_ONLY_TIMETRIALS,false);
        timerr = sharedPref.getBoolean(SettingsActivity.KEY_TIMER_TIMETRIALS,false);
        kidsmode = sharedPref.getBoolean(SettingsActivity.KEY_KIDS_MODE_SWITCH,false);
        if(!addition || !subtraction || !multiplication || !division || !timerr || kidsmode) {
            Toasty.error(TimeTrialsLoadingScreen.this,"High Score ranking disabled- check your settings",Toast.LENGTH_LONG,true).show();
        }
    }
    @Override
    public void onBackPressed() {

    }
}
