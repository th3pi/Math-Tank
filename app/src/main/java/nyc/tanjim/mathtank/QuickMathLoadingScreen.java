package nyc.tanjim.mathtank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class QuickMathLoadingScreen extends AppCompatActivity {
    private static int loadingScreenTime = 4000;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trials_loading_screen);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.timetrialsbg));
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.qboard_black));
            Toast.makeText(this,"Dark mode not compatible with QuickMath.",Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
        }
        TextView hintText = findViewById(R.id.hintText);
        Random rd = new Random();
        switch (rd.nextInt(4)){
            case 0:
                hintText.setText(getString(R.string.color));
                break;
            case 1:
                hintText.setText(getString(R.string.panic));
                break;
            case 2:
                hintText.setText(getString(R.string.wrongs));
                break;
            case 3:
                hintText.setText(getString(R.string.friends));
                break;
        }
        timer = findViewById(R.id.timeText);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),QuickMath.class));
                finish();
            }
        },loadingScreenTime);
        new CountDownTimer(loadingScreenTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(getString(R.string.loading_screen_timer, (int) millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    @Override
    public void onBackPressed() {

    }
}
