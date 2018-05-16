package nyc.tanjim.mathshark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    Button quickMathButton, timeTrialsButton, advancedMathButton;
    Animation fromLeftQuickMath, fromLeftTimeTrials, fromLeftAdvanced;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        quickMathButton = findViewById(R.id.quickMathsButton);
        timeTrialsButton = findViewById(R.id.timeTrialsButton);
        advancedMathButton = findViewById(R.id.advancedMathButton);

        fromLeftQuickMath = AnimationUtils.loadAnimation(this,R.anim.from_left_quick_math);
        quickMathButton.setAnimation(fromLeftQuickMath);
        fromLeftTimeTrials = AnimationUtils.loadAnimation(this,R.anim.from_left_time_trials);
        timeTrialsButton.setAnimation(fromLeftTimeTrials);
        fromLeftAdvanced = AnimationUtils.loadAnimation(this, R.anim.from_left_advanced);
        advancedMathButton.setAnimation(fromLeftAdvanced);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            backgroundAnimation();
        }

        PreferenceManager.setDefaultValues(this,R.xml.preference,false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        Toast.makeText(this,darkModePref.toString(), Toast.LENGTH_LONG).show();
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.question_board));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
        }
    }

    public void openAdvanced(View view){
        startActivity(new Intent(this, AdvancedLoadingScreen.class));
    }
    public void openQuickMaths(View view){
        startActivity(new Intent(this,QuickMathLoadingScreen.class));
    }
    public void openTimeTrials(View view){
        startActivity(new Intent(this, TimeTrialsLoadingScreen.class));
    }
    public void openSettings(View view){
        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void backgroundAnimation(){
            ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation));
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(5000);
            animationDrawable.setExitFadeDuration(6000);
            animationDrawable.start();
    }

}
