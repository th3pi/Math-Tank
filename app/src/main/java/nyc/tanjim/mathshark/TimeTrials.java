package nyc.tanjim.mathshark;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TimeTrials extends AppCompatActivity {
    TextView whichOneIsCorrect, timeLeftText, scoreText;
    Button button0, button1, button2, button3;
    ImageButton menuButtonTimeTrials;
    ArrayList<String> questions = new ArrayList<String>();
    int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_trials);
        whichOneIsCorrect = findViewById(R.id.whichOneIsCorrect);
        timeLeftText = findViewById(R.id.timeLeftText);
        scoreText = findViewById(R.id.scoreText);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        menuButtonTimeTrials = findViewById(R.id.menuButtonTimeTrials);
        generateQuestions();

        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftText.setText(timeLeft((int)millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    public String timeLeft(int millisUntilFinished){
        return String.format("Time left: %ds", millisUntilFinished);
    }

    public String correctEquation(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+1;
        switch (rd.nextInt(4)) {
            case 0:
                return String.format(Integer.toString(a) + " + " + Integer.toString(b) + " = " + Integer.toString(a + b));
            case 1:
                a = rd.nextInt(10)+1;
                b = rd.nextInt(10)+a;
                return String.format(Integer.toString(b) + " - " + Integer.toString(a) + " = " + Integer.toString(b - a));
            case 2:
                while(b % a != 0){
                    a = rd.nextInt(10)+1;
                    b = rd.nextInt(10)+a;
                }
                return String.format(Integer.toString(b) + " / " + Integer.toString(a) + " = " + Integer.toString(b / a));
            case 3:
                return String.format(Integer.toString(a) + " x " + Integer.toString(b) + " = " + Integer.toString(a * b));

            default:
                return "Oops something broke";
        }

    }
    public String wrongEquation(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+1;

        switch (rd.nextInt(4)) {
            case 0:
                return String.format(Integer.toString(rd.nextInt(10)+1) + " + " + Integer.toString(rd.nextInt(10)+1) + " = " + Integer.toString(rd.nextInt(20)+1));
            case 1:
                a = rd.nextInt(10)+1;
                b = rd.nextInt(10)+a;
                return String.format(Integer.toString(b) + " - " + Integer.toString(rd.nextInt(10)+1) + " = " + Integer.toString(rd.nextInt(10)+1));
            case 2:
                while(b % a != 0){
                    a = rd.nextInt(10)+1;
                    b = rd.nextInt(10)+a;
                }
                return String.format(Integer.toString(b) + " / " + Integer.toString(a) + " = " + Integer.toString(rd.nextInt(10)+1));
            case 3:
                return String.format(Integer.toString(a) + " x " + Integer.toString(b) + " = " + Integer.toString(rd.nextInt(10)+1));

            default:
                return "Oops something broke";
        }

    }
    public void generateQuestions(){
        Random rd = new Random();
        locationOfCorrectAnswer = rd.nextInt(4);
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                questions.add(correctEquation());
            }else {
                questions.add(wrongEquation());
            }

        }
        scoreText.setText(Integer.toString(score));
        button0.setText(questions.get(0));
        button1.setText(questions.get(1));
        button2.setText(questions.get(2));
        button3.setText(questions.get(3));
        questions.clear();

    }

    public void choose(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            numberOfQuestions++;
            generateQuestions();
            countDownTimer.start();
        }else {
            numberOfQuestions++;
            generateQuestions();
        }
    }

}
