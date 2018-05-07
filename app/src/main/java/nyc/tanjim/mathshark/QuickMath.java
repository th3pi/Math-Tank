package nyc.tanjim.mathshark;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class QuickMath extends AppCompatActivity {
    TextView timerText, quickMathQuestion, quickMathScore, scoreSpread, lastScoreText, winningMessage;
    TextView scoreMessage, iqMessage;
    Button correctButton, wrongButton, playAgainButton, quitButton;
    int correctAnswer, score, wrongOrCorrect, numberOfQuestions;
    Dialog scorePopUp;
    Vibrator vibrator;


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
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        generateQuestion();
        backgroundAnimation();
        timer();
    }
    //TODO create an algorithm to calibrate IQ
    public void showPopUp(){
        if(numberOfQuestions - score == 0 && numberOfQuestions > 10){
            winningMessage.setText("Hello there genius!");
        }else if(numberOfQuestions - score > 0 && numberOfQuestions - score < 5 && numberOfQuestions > 10) {
            winningMessage.setText("Unbelievable!");
        }else {
            winningMessage.setText("Need more practice!");
        }
        scoreMessage.setText(getString(R.string.score_pop_score, score, numberOfQuestions));
        scorePopUp.setCanceledOnTouchOutside(false);
        scorePopUp.show();
    }

    public void playAgain(View view){
        recreate();
    }
    public  void quit(View view){
        finish();
    }

    public void backgroundAnimation(){
        ConstraintLayout constraintLayout = (findViewById(R.id.quickMathBg));
        constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation));
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();
    }
    public void choose(View view){
        if(view.getTag().toString().equals(Integer.toString(wrongOrCorrect))){
            score++;
            generateQuestion();
            quickMathScore.setText("SCORE: " + Integer.toString(score));
        }else{
            generateQuestion();
            vibrator.vibrate(500);
        }

    }
    public void generateQuestion(){
        Random rd = new Random();
        wrongOrCorrect = rd.nextInt(2);
        if(wrongOrCorrect == 0){
            correctQuestion();
        }
        else{
            wrongQuestion();
        }
        numberOfQuestions++;
    }
    public void correctQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(12)+1;
        int b = rd.nextInt(12)+1;
        int questionType = rd.nextInt(4);
        if(questionType == 0){
            correctAnswer = a + b;
            quickMathQuestion.setText(Integer.toString(a) + "+" + Integer.toString(b) + "=" + Integer.toString(correctAnswer));
        }else if(questionType == 1){
            a = rd.nextInt(10)+1;
            b = rd.nextInt(10)+a;
            correctAnswer = b - a;
            quickMathQuestion.setText(Integer.toString(b) + "-" + Integer.toString(a) + "=" + Integer.toString(correctAnswer));
        }else if(questionType == 2){
            a = rd.nextInt(10)+1;
            b = rd.nextInt(10)+1;
            correctAnswer = a * b;
            quickMathQuestion.setText(Integer.toString(a) + "x" + Integer.toString(b) + "=" + Integer.toString(correctAnswer));
        }else if(questionType == 3){
            while(b % a != 0){
                a = rd.nextInt(12)+1;
                b = rd.nextInt(12)+1;
            }
            correctAnswer = b / a;
            quickMathQuestion.setText(Integer.toString(b) + "/" + Integer.toString(a) + "=" + Integer.toString(correctAnswer));
        }
    }
    public void wrongQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(12)+1;
        int b = rd.nextInt(12)+1;
        int randomAnswer;
        int questionType = rd.nextInt(4);
        if(questionType == 0){
            correctAnswer = a + b;
            randomAnswer = rd.nextInt(24)+1;
            while(randomAnswer == correctAnswer){
                randomAnswer = rd.nextInt(24)+1;
            }
            quickMathQuestion.setText(Integer.toString(a) + "+" + Integer.toString(b) + "=" + Integer.toString(randomAnswer));
        }else if(questionType == 1){
            a = rd.nextInt(10)+1;
            b = rd.nextInt(10)+a;
            correctAnswer = b - a;
            randomAnswer = rd.nextInt(24)+1;
            while(randomAnswer == correctAnswer){
                randomAnswer = rd.nextInt(24)+1;
            }
            quickMathQuestion.setText(Integer.toString(b) + "-" + Integer.toString(a) + "=" + Integer.toString(randomAnswer));
        }else if(questionType == 2){
            a = rd.nextInt(10)+1;
            b = rd.nextInt(10)+1;
            correctAnswer = a * b;
            randomAnswer = rd.nextInt(100)+1;
            while(randomAnswer == correctAnswer){
                randomAnswer = rd.nextInt(24)+1;
            }
            quickMathQuestion.setText(Integer.toString(a) + "x" + Integer.toString(b) + "=" + Integer.toString(randomAnswer));
        }else if(questionType == 3){
            while(b % a != 0){
                a = rd.nextInt(12)+1;
                b = rd.nextInt(12)+1;
            }
            correctAnswer = b / a;
            randomAnswer = rd.nextInt(24)+1;
            while(randomAnswer == correctAnswer){
                randomAnswer = rd.nextInt(24)+1;
            }
            quickMathQuestion.setText(Integer.toString(b) + "/" + Integer.toString(a) + "=" + Integer.toString(randomAnswer));
        }
    }

    //Timer that keeps track of time
    public void timer(){
        new CountDownTimer(30000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished > 10000)
                    timerText.setText("0" + ":" + Integer.toString((int) millisUntilFinished / 1000));
                else
                    timerText.setText("0" + ":0" + Integer.toString((int) millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
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
