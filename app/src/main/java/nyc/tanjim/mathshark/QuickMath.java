package nyc.tanjim.mathshark;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class QuickMath extends AppCompatActivity {
    TextView timerText, quickMathQuestion, quickMathScore, scoreSpread, winningMessage;
    TextView scoreMessage, iqMessage, userFeedback;
    Button correctButton, wrongButton, playAgainButton, quitButton;
    int correctAnswer, score, wrongOrCorrect, numberOfQuestions, feedBackNum;
    Dialog scorePopUp;
    Vibrator vibrator;
    Animation correctAnimation;
    Boolean addition,subtraction,multiplication,division, timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_math);
        timerText = findViewById(R.id.stopwatchText);
        quickMathQuestion = findViewById(R.id.quickMathQuestion);
        quickMathScore = findViewById(R.id.quickMathsScore);
        correctButton = findViewById(R.id.correctButton);
        wrongButton = findViewById(R.id.wrongButton);
        scoreSpread = findViewById(R.id.scorespread);
        scorePopUp = new Dialog(this);
        scorePopUp.setContentView(R.layout.score_popup);
        playAgainButton = scorePopUp.findViewById(R.id.playAgainButton);
        quitButton = scorePopUp.findViewById(R.id.quitButton);
        winningMessage = scorePopUp.findViewById(R.id.winningMessage);
        scoreMessage = scorePopUp.findViewById(R.id.scoreMessage);
        iqMessage = scorePopUp.findViewById(R.id.iqMessage);
        userFeedback = findViewById(R.id.plusOne);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        correctAnimation = AnimationUtils.loadAnimation(this, R.anim.correct_animation);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //Gets user preferences
        addition = sharedPref.getBoolean(SettingsActivity.KEY_ADDITION_ONLY_QUICKMATH,false);
        subtraction = sharedPref.getBoolean(SettingsActivity.KEY_SUBTRACTION_ONLY_QUICKMATH,false);
        multiplication = sharedPref.getBoolean(SettingsActivity.KEY_MULTIPLICATION_ONLY_QUICKMATH,false);
        division = sharedPref.getBoolean(SettingsActivity.KEY_DIVISION_ONLY_QUICKMATH,false);
        timer = sharedPref.getBoolean(SettingsActivity.KEY_TIMER,false);
//        timerDuration = sharedPref.getString(SettingsActivity.KEY_TIMER,"30");


        //Changes the background and status bar color when the timer hits 15 seconds
        generateQuestion();
        if(timer) {
            timer();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                ConstraintLayout constraintLayout = findViewById(R.id.quickMathBg);
                TransitionDrawable transitionDrawable = (TransitionDrawable) constraintLayout.getBackground();

                @Override
                public void run() {
                    transitionDrawable.startTransition(10000);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(Color.RED);
                    }
                }
            }, 15000);
        }
        else
            timerText.setText(getString(R.string.timer_disabled));
    }

    /**
    * Method to show pop up.
     * If score difference is less than 4 and number of questions answered are greater than 10
     * then user is top tier
     * Shows an afk text if user didn't answer any questions.
     * Doesn't let user tap outside pop up box
     *
    * */
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
        if(numberOfQuestions != 0 && score != 0) {
            iqMessage.setText(getString(R.string.shark_points, Math.round((numberOfQuestions + score) * 4)));
        }else {
            iqMessage.setText("");
        }
        scorePopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        scorePopUp.setCanceledOnTouchOutside(false);
        if(!QuickMath.this.isFinishing()) {
            scorePopUp.show();
        }
        scorePopUp.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
    }

    //Pop up Play again button
    public void playAgain(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), QuickMathLoadingScreen.class));
    }

    //Pop up quit button
    public  void quit(View view){
        finish();
    }


    /**The answer button
     * if user answers correct score increases by one
     * number of questions increases by one
     * score text updates
     * Feeds feedback to user depending on right or wrong answer
     * */
    public void choose(View view){
        userFeedback.startAnimation(AnimationUtils.loadAnimation(this,R.anim.flicker_animation));
        if(view.getTag().toString().equals(Integer.toString(wrongOrCorrect))){
            score++;
            generateQuestion();
            quickMathScore.setText(getString(R.string.score,score));
            numberOfQuestions++;
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
        }else{
            quickMathScore.startAnimation(AnimationUtils.loadAnimation(this,R.anim.correct_animation));
            vibrator.vibrate(500);
            if(wrongOrCorrect == 1){
                wrongButton.startAnimation(correctAnimation);
            }else{
                correctButton.startAnimation(correctAnimation);
            }
            Random rd = new Random();
            switch (rd.nextInt(3)) {
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
            generateQuestion();
            numberOfQuestions++;

        }
    }

    //Generates write or wrong question randomly
    public void generateQuestion(){
        Random rd = new Random();
        int questionType = rd.nextInt(4);
        feedBackNum = rd.nextInt(12);
        if(questionType == 0){
            if(addition) {
                sumQuestion();
            }else{
                if(subtraction) {
                    subtractQuestion();
                }else{
                    if(multiplication)
                        multiplyQuestions();
                    else{
                        if(division)
                            divisionQuestion();
                    }
            }
            }
        }else if(questionType == 1){
            if(subtraction) {
                subtractQuestion();
            }else{
                if(addition) {
                    sumQuestion();
                }else{
                    if(multiplication)
                        multiplyQuestions();
                    else{
                        if(division)
                            divisionQuestion();
                    }
                }
            }

        }else if(questionType == 2){
            if(multiplication) {
                multiplyQuestions();
            }else{
                if(addition) {
                    sumQuestion();
                }else{
                    if(subtraction)
                        subtractQuestion();
                    else{
                        if(division)
                            divisionQuestion();
                    }
                }
            }
        }else if(questionType == 3){
            if(division) {
                divisionQuestion();
            }else{
                if(addition) {
                    sumQuestion();
                }else{
                    if(subtraction)
                        subtractQuestion();
                    else{
                        if(multiplication)
                            multiplyQuestions();
                    }
                }
            }
        }
    }

    public void sumQuestion(){
        Random rd = new Random();
        int a = rd.nextInt((25-10)+1)+10;
        int b = rd.nextInt((25-10)+1)+10;
        wrongOrCorrect = rd.nextInt(2);
        int incorrectAnswer;
        if(wrongOrCorrect == 0){
            correctAnswer = a + b;
            quickMathQuestion.setText(getString(R.string.sum,a,b,correctAnswer));
        }else{
            incorrectAnswer = rd.nextInt((50-12)+1)+12;
            while(incorrectAnswer == correctAnswer){
                incorrectAnswer = rd.nextInt((50-12)+1)+12;
            }
            quickMathQuestion.setText(getString(R.string.sum,a,b,incorrectAnswer));
        }
    }
    public void subtractQuestion(){
        Random rd = new Random();
        int c = rd.nextInt(25)+1;
        int d = rd.nextInt(10)+c;
        int incorrectAnswer;
        wrongOrCorrect = rd.nextInt(2);
        if(wrongOrCorrect == 0 ) {
            quickMathQuestion.setText(Integer.toString(d) + " - " + Integer.toString(c) + " = " + Integer.toString(d - c));
        }else{
            correctAnswer = d - c;
            incorrectAnswer = rd.nextInt(20)+1;
            while(incorrectAnswer == correctAnswer){
                incorrectAnswer = rd.nextInt(20)+1;
            }
            quickMathQuestion.setText(Integer.toString(d) + " - " + Integer.toString(c) + " = " + Integer.toString(incorrectAnswer));
        }
    }
    public void multiplyQuestions(){
        Random rd = new Random();
        int a = rd.nextInt((12-1)+1)+1;
        int b = rd.nextInt((12-1)+1)+1;
        correctAnswer = a * b;
        wrongOrCorrect = rd.nextInt(2);
        int incorrectAnswer;
        if(wrongOrCorrect == 0){
            quickMathQuestion.setText(getString(R.string.mult,a,b,correctAnswer));
        }else{
            incorrectAnswer = rd.nextInt((100-20)+1)+20;
            while(incorrectAnswer == correctAnswer){
                incorrectAnswer = rd.nextInt((100-20)+1)+20;
            }
            quickMathQuestion.setText(getString(R.string.mult,a,b,incorrectAnswer));
        }
    }
    public void divisionQuestion(){
        Random rd = new Random();
        int a = rd.nextInt((25-10)+1)+10;
        int b = rd.nextInt((25-10)+1)+10;
        int incorrectAnswer;
        wrongOrCorrect = rd.nextInt(2);
        if(wrongOrCorrect == 0){
            while(b % a != 0){
                a = rd.nextInt(10)+1;
                b = rd.nextInt(10)+a;
            }
            correctAnswer = b / a;
            quickMathQuestion.setText(getString(R.string.div,b,a,correctAnswer));
        }else{
            incorrectAnswer = rd.nextInt(24)+1;
            correctAnswer = b / a;
            while(b % a != 0 || incorrectAnswer == correctAnswer){
                a = rd.nextInt(10)+1;
                b = rd.nextInt(10)+a;
            }
            quickMathQuestion.setText(getString(R.string.div,b,a,incorrectAnswer));
        }
    }

    //Timer that keeps track of time
    public void timer(){
//        int time = Integer.parseInt(timerDuration) * 1000;
        new CountDownTimer(30000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished > 10000)
                    timerText.setText(getString(R.string.timer_quick_math,(int) millisUntilFinished / 1000));
                else if(millisUntilFinished < 10000 && millisUntilFinished > 5000){
                    //Timer flickering gets faster as time runs out
                    //Adds a 0 before last digit
                    timerText.setText(getString(R.string.timer_quick_math_ten_less,(int) millisUntilFinished / 1000));
                    timerText.startAnimation(AnimationUtils.loadAnimation(QuickMath.this, R.anim.flicker_animation_2));
                }else if(millisUntilFinished < 5000 && millisUntilFinished > 3000){
                    timerText.setText(getString(R.string.timer_quick_math_ten_less,(int) millisUntilFinished / 1000));
                    timerText.startAnimation(AnimationUtils.loadAnimation(QuickMath.this, R.anim.flicker_animation_1));
                }else {
                    timerText.setText(getString(R.string.timer_quick_math_ten_less,(int) millisUntilFinished / 1000));
                    timerText.startAnimation(AnimationUtils.loadAnimation(QuickMath.this, R.anim.flicker_animation));
                }
            }
            @Override
            public void onFinish() {
                //Shows user results
               showPopUp();
            }
        }.start();
    }

    //Resets everything to zero
    public void reset(){
        numberOfQuestions = 0;
        score = 0;
        timer();
        generateQuestion();
    }
    //This method creates an alert box once the timer hits zero

    //this is redundant
    public void message(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your answered " + Integer.toString(score) + " correctly out of" +
                " " + Integer.toString(numberOfQuestions)).setTitle("Congratulations!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        if(!QuickMath.this.isFinishing()) {
            dialog.show();
        }
    }


}
