package nyc.tanjim.mathtank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import es.dmoral.toasty.Toasty;


public class AdvancedLoadingScreen extends AppCompatActivity {
    private static int loadingScreenTime = 4000;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trials_loading_screen);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        Boolean sqrt,sqr,cube,addition,subtraction,addmult,submult,adddiv,subdiv, mute, kidsmode, flashingText;
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.timetrialsbg));
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.qboard_black));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
        }
        TextView hintText = findViewById(R.id.hintText);
        Random rd = new Random();
        switch (rd.nextInt(4)){
            case 0:
                hintText.setText(getString(R.string.notime));
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
                startActivity(new Intent(getApplicationContext(),Advanced.class));
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

        sqr = sharedPref.getBoolean(SettingsActivity.KEY_SQUARE,false);
        sqrt = sharedPref.getBoolean(SettingsActivity.KEY_SQUARE_ROOT,false);
        cube = sharedPref.getBoolean(SettingsActivity.KEY_CUBE,false);
        addition = sharedPref.getBoolean(SettingsActivity.KEY_ADDITION_ADVANCED, false);
        subtraction = sharedPref.getBoolean(SettingsActivity.KEY_SUBTRACTION_ADVANCED,false);
        addmult = sharedPref.getBoolean(SettingsActivity.KEY_ADDITION_X_MULTIPLICATION,false);
        adddiv = sharedPref.getBoolean(SettingsActivity.KEY_ADDITION_BY_DIVISION,false);
        submult = sharedPref.getBoolean(SettingsActivity.KEY_SUBTRACTION_X_MULTIPLICATION,false);
        subdiv = sharedPref.getBoolean(SettingsActivity.KEY_SUBTRACTION_BY_DIVISION,false);
        kidsmode = sharedPref.getBoolean(SettingsActivity.KEY_KIDS_MODE_SWITCH,false);
        if(!addition || !subtraction || !adddiv || !addmult || !subdiv || !submult || !sqr || !sqrt || !cube || kidsmode) {
            Toasty.error(AdvancedLoadingScreen.this,"High Score ranking disabled- check your settings",Toast.LENGTH_LONG,true).show();
        }
    }
    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }
}
