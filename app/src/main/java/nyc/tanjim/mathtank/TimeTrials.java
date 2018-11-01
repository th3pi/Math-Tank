package nyc.tanjim.mathtank;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.shape.RectangleShape;

import static android.graphics.Color.GRAY;

public class TimeTrials extends AppCompatActivity {
    private TextView timeLeftText;
    private TextView scoreText;
    private TextView userFeedback;
    private TextView scoreMessage;
    private TextView iqMessage;
    private TextView winningMessage, whichOneIsCorrect;
    private Button button0, button1, button2, button3;
    int a, b;
    private ArrayList<String> questions = new ArrayList<String>();
    int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0, onARoll = 0, feedBackNum, wrongOrCorrect, musicLength;
    private CountDownTimer countDownTimer;
    private Animation correctAnimation, feedBackAnimation;
    private Dialog scorePopUp;
    private MediaPlayer mediaPlayer;
    private Boolean mute, flashingText;
    boolean ranBefore;
    private SharedPreferences scorePreference;
    private Chronometer chronometer;
    private long elapsedMillis;
    SharedPreferences sharedPref;
    ImageButton muteButton;


    //Boolean values to check user preference.
    private Boolean addition, subtraction, multiplication, division, timer, kidsmode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trials);
        scorePreference = getSharedPreferences("highScore",MODE_PRIVATE);
        chronometer = new Chronometer(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        TextView whichOneIsCorrect = findViewById(R.id.whichOneIsCorrect);
        timeLeftText = findViewById(R.id.timeLeftText);
        scoreText = findViewById(R.id.scoreText);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        userFeedback = findViewById(R.id.userFeedback);
        whichOneIsCorrect = findViewById(R.id.whichOneIsCorrect);
        scorePopUp = new Dialog(this);
        scorePopUp.setContentView(R.layout.score_popup);
        scorePopUp.getWindow().getAttributes().windowAnimations = R.style.ScorePopUpAnimation;
        scorePopUp.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
        winningMessage = scorePopUp.findViewById(R.id.winningMessage);
        scoreMessage = scorePopUp.findViewById(R.id.scoreMessage);
        iqMessage = scorePopUp.findViewById(R.id.iqMessage);
        muteButton = findViewById(R.id.muteButton);

        //Variables to hold animation values.
        correctAnimation = AnimationUtils.loadAnimation(this,R.anim.correct_animation);
        feedBackAnimation = AnimationUtils.loadAnimation(this,R.anim.flicker_animation);

        //Required to generate the first question
        //Initial animation
        button0.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_0));
        button1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_1));
        button2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_2));
        button3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_3));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(GRAY);
        }
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        mute = sharedPref.getBoolean(SettingsActivity.KEY_MUTE_MUSIC,false);
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.timetrialsbg));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.qboard_black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            button0.setBackground(getDrawable(R.drawable.main_menu_button_bg_dk));
            button1.setBackground(getDrawable(R.drawable.main_menu_button_og_dk));
            button2.setBackground(getDrawable(R.drawable.main_menu_button_rd_dk));
            button3.setBackground(getDrawable(R.drawable.main_menu_button_gr_dk));
            iqMessage.setBackground(getDrawable(R.drawable.main_menu_button_gr_dk));
            scoreMessage.setBackground(getDrawable(R.drawable.main_menu_button_bg_dk));
        }
        /*
          Required to get user's preferences
         */
        //Gets user preferences
        addition = sharedPref.getBoolean(SettingsActivity.KEY_ADDITION_ONLY_TIMETRIALS,false);
        subtraction = sharedPref.getBoolean(SettingsActivity.KEY_SUBTRACTION_ONLY_TIMETRIALS,false);
        multiplication = sharedPref.getBoolean(SettingsActivity.KEY_MULTIPLICATION_ONLY_TIMETRIALS,false);
        division = sharedPref.getBoolean(SettingsActivity.KEY_DIVISION_ONLY_TIMETRIALS,false);
        timer = sharedPref.getBoolean(SettingsActivity.KEY_TIMER_TIMETRIALS,false);
        kidsmode = sharedPref.getBoolean(SettingsActivity.KEY_KIDS_MODE_SWITCH,false);
        flashingText = sharedPref.getBoolean(SettingsActivity.KEY_FLASHING_TEXT,true);
        if(timer)
            timer();
        else {
            timeLeftText.setText(getString(R.string.timer_disabled));
        }
        //Initialize ads
        MobileAds.initialize(this,getString(R.string.timeTrialsAd));
        generateQuestions();
        //Ad load and requests
        AdView mAdView = findViewById(R.id.ttAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mediaPlayer = MediaPlayer.create(this,R.raw.retrosoul);
        mediaPlayer.setLooping(true);
        if(!mute) {
            mediaPlayer.start();
            muteButton.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }else{
            muteButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }

        if(isFirstTime()){
            countDownTimer.cancel();
            whichOneIsCorrect.setText("Restart to initiate timer");
            ShowcaseConfig config = new ShowcaseConfig();
            final MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "timeTrialsOnBoarding");
            config.setMaskColor(getResources().getColor(R.color.colorAccent50));
            config.setRenderOverNavigationBar(true);
            config.setShapePadding(50);
            config.setDelay(500);
            sequence.setConfig(config);

            timeLeftText.post(new Runnable() {
                @Override
                public void run() {
                    sequence.addSequenceItem(timeLeftText,"Welcome to TimeTrials! You have 8 seconds to find the correct equation. Timer resets if you find the correct one.","Next");

                    switch (locationOfCorrectAnswer){
                        case 0: sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(TimeTrials.this)
                                        .setTarget(button0)
                                        .setContentText("I'll help you with this one - since the correct answer is " + button0.getText() + ". Tap on the button. You lose if you don't select the correct equation.")
                                        .setMaskColour(getResources().getColor(R.color.colorAccent50))
                                        .setDismissOnTargetTouch(true)
                                        .setTargetTouchable(true)
                                        .withRectangleShape()
                                        .build()
                        );
                            break;
                        case 1: sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(TimeTrials.this)
                                        .setTarget(button1)
                                        .setContentText("I'll help you with this one - since the correct answer is " + button1.getText() + ". Tap on the button. You lose if you don't select the correct equation.")
                                        .setMaskColour(getResources().getColor(R.color.colorAccent50))
                                        .setDismissOnTargetTouch(true)
                                        .setTargetTouchable(true)
                                        .withRectangleShape()
                                        .build()
                        );
                            break;
                        case 2: sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(TimeTrials.this)
                                        .setTarget(button2)
                                        .setContentText("I'll help you with this one - since the correct answer is " + button2.getText() + ". Tap on the button. You lose if you don't select the correct equation.")
                                        .setMaskColour(getResources().getColor(R.color.colorAccent50))
                                        .setDismissOnTargetTouch(true)
                                        .setTargetTouchable(true)
                                        .withRectangleShape()
                                        .build()
                        );
                            break;
                        case 3: sequence.addSequenceItem(
                                new MaterialShowcaseView.Builder(TimeTrials.this)
                                        .setTarget(button3)
                                        .setContentText("I'll help you with this one - since the correct answer is " + button3.getText() + ". Tap on the button. You lose if you don't select the correct equation.")
                                        .setMaskColour(getResources().getColor(R.color.colorAccent50))
                                        .setDismissOnTargetTouch(true)
                                        .setTargetTouchable(true)
                                        .withRectangleShape()
                                        .build()
                        );
                            break;
                    }
                    sequence.addSequenceItem(userFeedback,"You will get a feedback based on your answer. Words of encouragement!","Next");
                    sequence.addSequenceItem(scoreText,"This is your... Score. Pretty simple. You're all set! Tap on a wrong equation to bring up the scoreboard.","Done");
                    sequence.start();
                }
            });

        }
    }

    public void muteTemp(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            sharedPref.edit().putBoolean(SettingsActivity.KEY_MUTE_MUSIC,true).apply();
            muteButton.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }else{
            mediaPlayer.start();
            sharedPref.edit().putBoolean(SettingsActivity.KEY_MUTE_MUSIC,false).apply();
            muteButton.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying() && !mute) {
            musicLength = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
        if(addition && subtraction && multiplication && division && timer && !kidsmode) {
            finish();
        }
        if(timer){
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!mediaPlayer.isPlaying() && !mute) {
            mediaPlayer.seekTo(musicLength);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scorePopUp.dismiss();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if(addition && subtraction && multiplication && division && timer && !kidsmode) {
            Toasty.warning(getApplicationContext(), "Exiting Timetrials - you cannot leave the app while in a session", Toast.LENGTH_LONG, true).show();
        }
        if(timer){
            Toasty.warning(getApplicationContext(), "Exiting Timetrials - you cannot leave the app while timer is on", Toast.LENGTH_LONG, true).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void timer(){
        countDownTimer = new CountDownTimer(9000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 3000)
                    if(flashingText) {
                        timeLeftText.startAnimation(AnimationUtils.loadAnimation(TimeTrials.this, R.anim.timer_flicker));
                    }
                if (millisUntilFinished > 10000)
                    timeLeftText.setText(getString(R.string.time_left, (int) millisUntilFinished / 1000));
                else
                    timeLeftText.setText(getString(R.string.time_left_ten_less, (int) millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                showPopUp();
            }
        }.start();
    }
    public void showPopUp(){
        boolean newHigh = false;
        if(timer) {
            countDownTimer.cancel();
        }
        elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        int timeTaken = (int) elapsedMillis / 1000;
        if(addition && subtraction && multiplication && division && timer && !kidsmode) {
            SharedPreferences.Editor editor = scorePreference.edit();
            int largest = scorePreference.getInt("timeTrialsHighScore", 0);
            int largestTimeTaken = scorePreference.getInt("tTimeTaken", 1200);
            float hiddenElo = (float) score / timeTaken;
            float elo = scorePreference.getFloat("hiddenElo",0);
//            Log.i("SCORE", Integer.toString(score));
//            Log.i("TIME TAKEN", Integer.toString(timeTaken));
//            Log.i("HIDDEN ELO", Float.toString(hiddenElo));
            if (hiddenElo > elo) {
                largest = score;
                editor.putFloat("hiddenElo",hiddenElo).apply();
                editor.putInt("timeTrialsHighScore", largest).apply();
                editor.putInt("tTimeTaken", timeTaken).apply();
                Log.i("HIDDEN ELO", Float.toString(scorePreference.getFloat("hiddenElo",0)));
                chronometer.stop();
                newHigh = true;
            }
        }
        if(newHigh){
            winningMessage.setText("NEW HIGH SCORE!");
        }else {
            if (numberOfQuestions - score < 4 && numberOfQuestions > 10) {
                winningMessage.setText(getString(R.string.hey_there_genius));
            } else if (numberOfQuestions - score > 0 && numberOfQuestions - score < 5) {
                winningMessage.setText(getString(R.string.unbelievable));
            } else if (numberOfQuestions < 10 && numberOfQuestions > 1) {
                winningMessage.setText(getString(R.string.are_you_even));
            } else if (numberOfQuestions == 0) {
                winningMessage.setText(getString(R.string.afk_text));
            } else {
                winningMessage.setText(getString(R.string.need_more_practice));
            }
        }
        scoreMessage.setText(Integer.toString(score));
//        if(numberOfQuestions >= 10) {
//            if (numberOfQuestions - score >= 0 && numberOfQuestions - score < 2) {
//                iqMessage.setText(getString(R.string.exceptional_math_skill));
//            } else if (numberOfQuestions - score >= 0 && numberOfQuestions - score <= 3) {
//                iqMessage.setText(getString(R.string.above_average_math_skill));
//            } else if (numberOfQuestions - score >= 3 && numberOfQuestions - score <= 5) {
//                iqMessage.setText(getString(R.string.average_math_skill));
//            } else{
//                iqMessage.setText(getString(R.string.below_average_math_skill));
//            }
//        }else{
//            iqMessage.setText(getString(R.string.number_too_low_10));
//        }
        TextView typeText = scorePopUp.findViewById(R.id.typeText);
        typeText.setText("Time taken in seconds");
        iqMessage.setText(Integer.toString(timeTaken));
        scorePopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        scorePopUp.setCanceledOnTouchOutside(false);
        if(!TimeTrials.this.isFinishing()) {
            scorePopUp.show();
        }
        scorePopUp.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
    }
    public void playAgain(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), TimeTrialsLoadingScreen.class));
    }



    //Pop up quit button
    public  void quit(View view){
        finish();
    }
    public String sumQuestion(){
        Random rd = new Random();
        int a = rd.nextInt((15-4)+1)+4;
        int b = rd.nextInt((15-4)+1)+4;
        return getString(R.string.sum, a, b, a + b);

    }
    public String subtractQuestion(){
        Random rd = new Random();
        int a = rd.nextInt((15-4)+1)+4;
        int b = rd.nextInt((15-a)+1)+a;
        return getString(R.string.sub,b,a,b-a);
    }
    public String multiplyQuestion(){
        Random rd = new Random();
        int a = rd.nextInt((12-1)+1)+1;
        int b = rd.nextInt((12-1)+1)+1;
        return getString(R.string.mult,a,b,a*b);

    }
    public String divisionQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(50)+1;
        while(b % a != 0) {
                a = rd.nextInt(10)+1;
                b = rd.nextInt(50)+1;
            }
        return getString(R.string.div,b,a,b/a);

    }
    public String wrongTypeOfQuestion(){
        Random rd = new Random();
        switch (rd.nextInt(4)){
            case 0:
                if(addition){
                    int a = rd.nextInt(15)+1;
                    int b = rd.nextInt(12)+1;
                    int c = rd.nextInt(40)+1;
                    while(c == a+b){
                        c = rd.nextInt(40)+1;
                    }
                    return getString(R.string.sum,a,b,c);
                }else{
                    if(subtraction){
                        int a = rd.nextInt(15)+1;
                        int b = rd.nextInt((10-5)+1)+5;
                        int c = rd.nextInt(15)+1;
                        while(c == a-b){
                            c = rd.nextInt(15)+1;
                        }
                        return getString(R.string.sub,a,b,c);
                    }else{
                        if(multiplication){
                            int a = rd.nextInt(12)+1;
                            int b = rd.nextInt(12)+1;
                            int c = rd.nextInt(50)+1;
                            while(c == a*b){
                                c = rd.nextInt(50)+1;
                            }
                            return getString(R.string.mult,a,b,c);
                        }else{
                            if(division){
                                int a = rd.nextInt(25)+1;
                                int b = rd.nextInt(12)+1;
                                int c = rd.nextInt(12)+1;
                                while(c == a/b){
                                    c = rd.nextInt(12)+1;
                                }
                                return getString(R.string.div,a,b,c);
                            }
                        }
                    }
                }
            case 1:
                if(subtraction){
                    int a = rd.nextInt(15)+1;
                    int b = rd.nextInt((10-5)+1)+5;
                    int c = rd.nextInt(15)+1;
                    while(c == a-b){
                        c = rd.nextInt(15)+1;
                    }
                    return getString(R.string.sub,a,b,c);
                }else{
                    if(addition){
                        int a = rd.nextInt(15)+1;
                        int b = rd.nextInt(12)+1;
                        int c = rd.nextInt(40)+1;
                        while(c == a+b){
                            c = rd.nextInt(40)+1;
                        }
                        return getString(R.string.sum,a,b,c);
                    }else{
                        if(multiplication){
                            int a = rd.nextInt(12)+1;
                            int b = rd.nextInt(12)+1;
                            int c = rd.nextInt(50)+1;
                            while(c == a*b){
                                c = rd.nextInt(50)+1;
                            }
                            return getString(R.string.mult,a,b,c);
                        }else{
                            if(division){
                                int a = rd.nextInt(25)+1;
                                int b = rd.nextInt(12)+1;
                                int c = rd.nextInt(12)+1;
                                while(c == a/b){
                                    c = rd.nextInt(12)+1;
                                }
                                return getString(R.string.div,a,b,c);
                            }
                        }
                    }
                }
            case 2:
                if(multiplication){
                    int a = rd.nextInt(12)+1;
                    int b = rd.nextInt(12)+1;
                    int c = rd.nextInt(50)+1;
                    while(c == a*b){
                        c = rd.nextInt(50)+1;
                    }
                    return getString(R.string.mult,a,b,c);
                }else{
                    if(addition){
                        int a = rd.nextInt(15)+1;
                        int b = rd.nextInt(12)+1;
                        int c = rd.nextInt(40)+1;
                        while(c == a+b){
                            c = rd.nextInt(40)+1;
                        }
                        return getString(R.string.sum,a,b,c);
                    }else{
                        if(subtraction){
                            int a = rd.nextInt(15)+1;
                            int b = rd.nextInt((10-5)+1)+5;
                            int c = rd.nextInt(15)+1;
                            while(c == a-b){
                                c = rd.nextInt(15)+1;
                            }
                            return getString(R.string.sub,a,b,c);
                        }else{
                            if(division){
                                int a = rd.nextInt(25)+1;
                                int b = rd.nextInt(12)+1;
                                int c = rd.nextInt(12)+1;
                                while(c == a/b){
                                    c = rd.nextInt(12)+1;
                                }
                                return getString(R.string.div,a,b,c);
                            }
                        }
                    }
                }
            case 3:
                if(division){
                    int a = rd.nextInt(25)+1;
                    int b = rd.nextInt(12)+1;
                    int c = rd.nextInt(12)+1;
                    while(c == a/b){
                        c = rd.nextInt(12)+1;
                    }
                    return getString(R.string.div,a,b,c);
                }else{
                    if(addition){
                        int a = rd.nextInt(15)+1;
                        int b = rd.nextInt(12)+1;
                        int c = rd.nextInt(40)+1;
                        while(c == a+b){
                            c = rd.nextInt(40)+1;
                        }
                        return getString(R.string.sum,a,b,c);
                    }else{
                        if(subtraction){
                            int a = rd.nextInt(15)+1;
                            int b = rd.nextInt((10-5)+1)+5;
                            int c = rd.nextInt(15)+1;
                            while(c == a-b){
                                c = rd.nextInt(15)+1;
                            }
                            return getString(R.string.sub,a,b,c);
                        }else{
                            if(multiplication){
                                int a = rd.nextInt(12)+1;
                                int b = rd.nextInt(12)+1;
                                int c = rd.nextInt(50)+1;
                                while(c == a*b){
                                    c = rd.nextInt(50)+1;
                                }
                                return getString(R.string.mult,a,b,c);
                            }
                        }
                    }
                }
            default:
                return "Enable at least one type of question.";
        }
    }
    public String typeOfQuestion(){
        Random rd = new Random();
        switch (rd.nextInt(4)){
            case 0:
                if(addition) {
                    return sumQuestion();
                }else{
                    if(subtraction){
                        return subtractQuestion();
                    }else{
                        if(division){
                            return divisionQuestion();
                        }else{
                            if(multiplication){
                                return multiplyQuestion();
                            }
                        }
                    }
                }
            case 1:
                if(subtraction) {
                    return subtractQuestion();
                }else{
                    if(addition){
                        return sumQuestion();
                    }else{
                        if(division){
                            return divisionQuestion();
                        }else{
                            if(multiplication){
                                return multiplyQuestion();
                            }
                        }
                    }
                }
            case 2:
                if(multiplication){
                    return multiplyQuestion();
                }else{
                    if(addition){
                        return sumQuestion();
                    }else{
                        if(subtraction){
                            return subtractQuestion();
                        }else{
                            if(division){
                                return divisionQuestion();
                            }
                        }
                    }
                }
            case 3:
                if(division){
                    return divisionQuestion();
                }else{
                    if(addition){
                        return sumQuestion();
                    }else{
                        if(subtraction){
                            return subtractQuestion();
                        }else{
                            if(multiplication){
                                return multiplyQuestion();
                            }
                        }
                    }
                }
            default:
                return "Enable at least one type of question.";
        }
    }
    public void generateQuestions(){
        Random rd = new Random();
        wrongOrCorrect = rd.nextInt(2);
        locationOfCorrectAnswer = rd.nextInt(4);
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                questions.add(typeOfQuestion());
            }else{
                questions.add(wrongTypeOfQuestion());
            }
        }
        feedBackNum = rd.nextInt(12);
        scoreText.setText(getString(R.string.score, score));
        button0.setText(questions.get(0));
        button1.setText(questions.get(1));
        button2.setText(questions.get(2));
        button3.setText(questions.get(3));
        questions.clear();
    }

    public void choose(View view){
        if(flashingText) {
            userFeedback.startAnimation(feedBackAnimation);
        }
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            numberOfQuestions++;
            generateQuestions();
            if(timer && ranBefore) {
                countDownTimer.start();
            }
            onARoll++;
            if(feedBackNum == 0 || numberOfQuestions == 1){
                userFeedback.setText(getString(R.string.good_job));
            }else if(feedBackNum == 1){
                userFeedback.setText(getString(R.string.amazing));
            }else if(feedBackNum == 2){
                userFeedback.setText(getString(R.string.fantastic));
            }else if(feedBackNum == 3){
                userFeedback.setText(getString(R.string.damn));
            }else if(feedBackNum == 4){
                userFeedback.setText(getString(R.string.genius));
            }else if(feedBackNum == 5){
                userFeedback.setText(getString(R.string.sweet));
            }else if(feedBackNum == 6){
                userFeedback.setText(getString(R.string.crazy));
            }else if(feedBackNum == 7){
                userFeedback.setText(getString(R.string.keep));
            }else if(feedBackNum == 8){
                userFeedback.setText(getString(R.string.unbelievable));
            }else if(feedBackNum == 9){
                userFeedback.setText(getString(R.string.surprised));
            }else if(feedBackNum == 10){
                userFeedback.setText(getString(R.string.brilliant));
            }else if(feedBackNum == 11){
                userFeedback.setText(getString(R.string.bananas));
            }
        }else {
            onARoll = 0;
            numberOfQuestions++;
            if(locationOfCorrectAnswer == 0){
                button0.startAnimation(correctAnimation);
            }else if(locationOfCorrectAnswer == 1){
                button1.startAnimation(correctAnimation);
            }else if(locationOfCorrectAnswer == 2){
                button2.startAnimation(correctAnimation);
            }else if(locationOfCorrectAnswer == 3){
                button3.startAnimation(correctAnimation);
            }
            Random rd = new Random();
            switch (rd.nextInt(3)){
                case 0:
                    userFeedback.setText(getString(R.string.ohno));
                    break;
                case 1:
                    userFeedback.setText(getString(R.string.next));
                    break;
                case 2:
                    userFeedback.setText(getString(R.string.sad));
                    break;
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPopUp();
                }
            }, 1000);


            /*
            switch (locationOfCorrectAnswer){
                case 0:
                    button0.startAnimation(correctAnimation);
                    break;
                case 1:
                    button1.startAnimation(correctAnimation);
                    break;
                case 2:
                    button2.startAnimation(correctAnimation);
                    break;
                case 3:
                    button3.startAnimation(correctAnimation);
                default:
                    Log.i("Error","Something went wrong");
            }*/
        }
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

}
