package nyc.tanjim.mathtank;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
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

import nyc.MusicService;

//App ID Admob: ca-app-pub-3697147059223741~6847967899

public class MainMenu extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    Button quickMathButton, timeTrialsButton, advancedMathButton;
    Animation fromLeftQuickMath, fromLeftTimeTrials, fromLeftAdvanced;
    Boolean darkModePref;
    SharedPreferences sharedPref;
    AdView mAdView;
    InterstitialAd interstitialAd;
    boolean mIsBound = false;
    MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    public void doBindService(){
        Intent bindIntent = new Intent(this,MusicService.class);
        mIsBound = bindService(bindIntent,Scon, BIND_AUTO_CREATE);
    }

    public void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        quickMathButton = findViewById(R.id.quickMathsButton);
        timeTrialsButton = findViewById(R.id.timeTrialsButton);
        advancedMathButton = findViewById(R.id.advancedMathButton);


        //Animation for the game mode buttons
        fromLeftQuickMath = AnimationUtils.loadAnimation(this,R.anim.from_left_quick_math);
        quickMathButton.setAnimation(fromLeftQuickMath);
        fromLeftTimeTrials = AnimationUtils.loadAnimation(this,R.anim.from_left_time_trials);
        timeTrialsButton.setAnimation(fromLeftTimeTrials);
        fromLeftAdvanced = AnimationUtils.loadAnimation(this, R.anim.from_left_advanced);
        advancedMathButton.setAnimation(fromLeftAdvanced);
        PreferenceManager.setDefaultValues(this,R.xml.preference,false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(darkModePref) {
                backgroundAnimationDark();
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }else{
                backgroundAnimation();
            }
        }
        //Initialize ads
        MobileAds.initialize(this,getString(R.string.mainMenuad));
        //Ad load and requests
        mAdView = findViewById(R.id.mainMenuAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Advanced Math ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.advancedAd));
        interstitialAd.loadAd(new AdRequest.Builder().build());
//        if(interstitialAd.isLoaded()) {
//            interstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("79D83184DB5A6598E2EEE48303022BE4").build());
//                    openAdv();
//                }
//            });
//        }
        //Initializes music
        doBindService();
        startService(new Intent(this, MusicService.class));
    }
    @Override

    protected void onPause() {
        super.onPause();
        mServ.pauseMusic();
    }
    @Override

    protected void onRestart() {
        super.onRestart();
        mServ.resumeMusic();
    }
    @Override

    protected void onDestroy() {
        super.onDestroy();
        mServ.stopMusic();
        doUnbindService();
        stopService(new Intent(this, MusicService.class));
    }

    public void openAdvanced(View view){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                    openAdv();
                }
            });
        }else{
            startActivity(new Intent(this, AdvancedLoadingScreen.class));
        }
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
    public void backgroundAnimationDark(){
        ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
        constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation_dark));
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
