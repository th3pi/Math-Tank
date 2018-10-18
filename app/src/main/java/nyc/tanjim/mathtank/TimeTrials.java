package nyc.tanjim.mathtank;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


import static android.graphics.Color.GRAY;

public class TimeTrials extends AppCompatActivity {
    private TextView timeLeftText;
    private TextView scoreText;
    private TextView userFeedback;
    private TextView scoreMessage;
    private TextView iqMessage;
    private TextView winningMessage;
    private Button button0, button1, button2, button3;
    int a, b;
    private ArrayList<String> questions = new ArrayList<String>();
    int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0, onARoll = 0, feedBackNum, wrongOrCorrect, musicLength;
    private CountDownTimer countDownTimer;
    private Animation correctAnimation, feedBackAnimation;
    private Dialog scorePopUp;
    private MediaPlayer mediaPlayer;
    private Boolean mute;


    //Boolean values to check user preference.
    private Boolean addition, subtraction, multiplication, division, timer, kidsmode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trials);
        TextView whichOneIsCorrect = findViewById(R.id.whichOneIsCorrect);
        timeLeftText = findViewById(R.id.timeLeftText);
        scoreText = findViewById(R.id.scoreText);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        userFeedback = findViewById(R.id.userFeedback);
        scorePopUp = new Dialog(this);
        scorePopUp.getWindow().getAttributes().windowAnimations = R.style.ScorePopUpAnimation;
        scorePopUp.setContentView(R.layout.score_popup);
        winningMessage = scorePopUp.findViewById(R.id.winningMessage);
        scoreMessage = scorePopUp.findViewById(R.id.scoreMessage);
        iqMessage = scorePopUp.findViewById(R.id.iqMessage);

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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkModePref = sharedPref.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH, false);
        mute = sharedPref.getBoolean(SettingsActivity.KEY_MUTE_MUSIC,false);
        if(darkModePref){
            ConstraintLayout constraintLayout = (findViewById(R.id.timetrialsbg));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.qboard_black));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.qboard_black));
            }
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
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying() && !mute) {
            musicLength = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
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
        mediaPlayer.stop();
        mediaPlayer.release();
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
                    timeLeftText.startAnimation(AnimationUtils.loadAnimation(TimeTrials.this, R.anim.timer_flicker));
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
        if(numberOfQuestions - score < 4 && numberOfQuestions > 10){
            winningMessage.setText(getString(R.string.hey_there_genius));
        }else if(numberOfQuestions - score > 0 && numberOfQuestions - score < 5 && numberOfQuestions > 10) {
            winningMessage.setText(getString(R.string.unbelievable));
        }else if(numberOfQuestions < 10 && numberOfQuestions > 1){
            winningMessage.setText(getString(R.string.are_you_even));
        }else if(numberOfQuestions == 0){
            winningMessage.setText(getString(R.string.afk_text));
        }else {
            winningMessage.setText(getString(R.string.need_more_practice));
        }
        scoreMessage.setText(getString(R.string.score_pop_score, score, numberOfQuestions));
        if(numberOfQuestions - score >= 0 && numberOfQuestions - score < 2 && numberOfQuestions > 30) {
            iqMessage.setText(getString(R.string.exceptional_math_skill));
        }
        else if(numberOfQuestions - score > 2 && numberOfQuestions - score<  3 && numberOfQuestions > 30) {
            iqMessage.setText(getString(R.string.above_average_math_skill));
        }else if(numberOfQuestions - score > 3 && numberOfQuestions - score < 5 && numberOfQuestions > 30 ){
            iqMessage.setText(getString(R.string.average_math_skill));
        }else if(numberOfQuestions - score > 5 && numberOfQuestions - score < 7 && numberOfQuestions > 30){
            iqMessage.setText(getString(R.string.below_average_math_skill));
        }
        else if(numberOfQuestions < 30) {
            iqMessage.setText(getString(R.string.number_too_low_30));
        }else{
            iqMessage.setText(getString(R.string.score_too_low));
        }
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
                    return getString(R.string.sum, rd.nextInt(15)+1,rd.nextInt(12)+1,rd.nextInt(40)+1);
                }else{
                    if(subtraction){
                        return getString(R.string.sub,rd.nextInt(15)+1,rd.nextInt((10-5)+1)+5,rd.nextInt(15)+1);
                    }else{
                        if(multiplication){
                            return getString(R.string.mult,rd.nextInt(12)+1,rd.nextInt(12)+1,rd.nextInt(50)+1);
                        }else{
                            if(division){
                                return getString(R.string.div,rd.nextInt(25)+1,rd.nextInt(12)+1,rd.nextInt(12)+1);
                            }
                        }
                    }
                }
            case 1:
                if(subtraction){
                    return getString(R.string.sub,rd.nextInt(15)+1,rd.nextInt((10-5)+1)+5,rd.nextInt(15)+1);
                }else{
                    if(addition){
                        return getString(R.string.sum, rd.nextInt(15)+1,rd.nextInt(12)+1,rd.nextInt(40)+1);
                    }else{
                        if(multiplication){
                            return getString(R.string.mult,rd.nextInt(12)+1,rd.nextInt(12)+1,rd.nextInt(50)+1);
                        }else{
                            if(division){
                                return getString(R.string.div,rd.nextInt(25)+1,rd.nextInt(12)+1,rd.nextInt(12)+1);
                            }
                        }
                    }
                }
            case 2:
                if(multiplication){
                    return getString(R.string.mult,rd.nextInt(12)+1,rd.nextInt(12)+1,rd.nextInt(50)+1);
                }else{
                    if(addition){
                        return getString(R.string.sum, rd.nextInt(15)+1,rd.nextInt(12)+1,rd.nextInt(40)+1);
                    }else{
                        if(subtraction){
                            return getString(R.string.sub,rd.nextInt(15)+1,rd.nextInt((10-5)+1)+5,rd.nextInt(15)+1);
                        }else{
                            if(division){
                                return getString(R.string.div,rd.nextInt(25)+1,rd.nextInt(12)+1,rd.nextInt(12)+1);
                            }
                        }
                    }
                }
            case 3:
                if(division){
                    return getString(R.string.div,rd.nextInt(25)+1,rd.nextInt(12)+1,rd.nextInt(12)+1);
                }else{
                    if(addition){
                        return getString(R.string.sum, rd.nextInt(15)+1,rd.nextInt(12)+1,rd.nextInt(40)+1);
                    }else{
                        if(subtraction){
                            return getString(R.string.sub,rd.nextInt(15)+1,rd.nextInt((10-5)+1)+5,rd.nextInt(15)+1);
                        }else{
                            if(multiplication){
                                return getString(R.string.mult,rd.nextInt(12)+1,rd.nextInt(12)+1,rd.nextInt(50)+1);
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
        userFeedback.startAnimation(feedBackAnimation);
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            numberOfQuestions++;
            generateQuestions();
            if(timer) {
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

}
