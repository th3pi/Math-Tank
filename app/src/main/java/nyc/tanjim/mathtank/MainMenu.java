package nyc.tanjim.mathtank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

//App ID Admob: ca-app-pub-3697147059223741~6847967899

public class MainMenu extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    Button quickMathButton, timeTrialsButton, advancedMathButton;
    Animation fromLeftQuickMath, fromLeftTimeTrials, fromLeftAdvanced;
    AdView mAdView;
    InterstitialAd interstitialAd;
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
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.question_board));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
        }

        //Initialize ads
        MobileAds.initialize(this,getString(R.string.testAd));

        //Ad load and requests
        mAdView = findViewById(R.id.mainMenuAd);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("79D83184DB5A6598E2EEE48303022BE4").build();
        mAdView.loadAd(adRequest);
        //Advanced Math ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("79D83184DB5A6598E2EEE48303022BE4").build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("79D83184DB5A6598E2EEE48303022BE4").build());
                openAdv();
            }
        });
    }

    public void openAdvanced(View view){
        interstitialAd.show();
//        startActivity(new Intent(this, AdvancedLoadingScreen.class));
    }
    public void openAdv(){
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
    public void openAppInfo(View View){
        startActivity(new Intent(this,AppInfo.class));
    }

    public void backgroundAnimation(){
            ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation));
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(5000);
            animationDrawable.setExitFadeDuration(6000);
            animationDrawable.start();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(SettingsActivity.KEY_DARK_MODE_SWITCH)){
            recreate();
        }
    }
}
