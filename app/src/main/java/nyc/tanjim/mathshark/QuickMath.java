package nyc.tanjim.mathshark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuickMath extends AppCompatActivity {
    TextView stopWatchText, quickMathQuestion, quickMathScore;
    Button correctButton, wrongButton;
    int correctAnswer, score, wrongOrCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_math);
        stopWatchText = findViewById(R.id.stopwatchText);
        quickMathQuestion = findViewById(R.id.quickMathQuestion);
        quickMathScore = findViewById(R.id.quickMathsScore);
        correctButton = findViewById(R.id.correctButton);
        wrongButton = findViewById(R.id.wrongButton);
        generateQuestion();
    }
    public void choose(View view){
        if(view.getTag().toString().equals(Integer.toString(wrongOrCorrect))){
            score++;
            generateQuestion();
            quickMathScore.setText("SCORE: " + Integer.toString(score));
            Toast.makeText(this,"CORRECT",Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(this,"WRONG",Toast.LENGTH_SHORT);
            generateQuestion();
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
            correctAnswer = a - b;
            quickMathQuestion.setText(Integer.toString(a) + "-" + Integer.toString(b) + "=" + Integer.toString(correctAnswer));
        }else if(questionType == 2){
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
            correctAnswer = a - b;
            randomAnswer = rd.nextInt(24)+1;
            while(randomAnswer == correctAnswer){
                randomAnswer = rd.nextInt(24)+1;
            }
            quickMathQuestion.setText(Integer.toString(a) + "-" + Integer.toString(b) + "=" + Integer.toString(randomAnswer));
        }else if(questionType == 2){
            correctAnswer = a * b;
            randomAnswer = rd.nextInt(24)+1;
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
}
