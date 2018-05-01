package nyc.tanjim.mathshark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Advanced extends AppCompatActivity {
    Button button0, button1, button2, button3;
    TextView questionText, scoreView;
    int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0;
    ArrayList<Integer> answers = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_maths);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        questionText = findViewById(R.id.quiestionsView);
        scoreView = findViewById(R.id.scoreView);

        generateQuestion();
    }
    public void choose(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            generateQuestion();
            score++;
            numberOfQuestions++;
        }else {
            numberOfQuestions++;
        }

        answers.clear();
    }
    public void generateQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(4);
        if(a == 0){
            sumQuestions();
        }else if(a == 1){
            subtractQuestion();
        }else if(a == 2){
            multiplyQuestions();
        }else if(a == 3){
            divisionQuestion();
        }
    }

    public void sumQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(20)+1;
        int b = rd.nextInt(20)+1;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(a) + "+" + Integer.toString(b));
        for(int i = 0; i < 4; i++){
        //Selects where the right answer will be
        if(i == locationOfCorrectAnswer){
            answers.add(a + b);
        }else {
            incorrectAnswer = rd.nextInt(40)+1;

            //incorrectAnswer will be reevaluated if there's already an integer equal to it there
            //also checks whether there's already an incorrectAnswer with same value
            while(incorrectAnswer == a + b || answers.contains(incorrectAnswer)){
                  incorrectAnswer = rd.nextInt(40)+1;
            }
            answers.add(incorrectAnswer);
          }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void multiplyQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(12)+1;
        int b = rd.nextInt(12)+1;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(a) + "x" + Integer.toString(b));
        for(int i = 0; i < 4; i++){
            //Selects where the right answer will be
            if(i == locationOfCorrectAnswer){
                answers.add(a * b);
            }else {
                incorrectAnswer = rd.nextInt(144)+1;

                //incorrectAnswer will be reevaluated if there's already an integer equal to it there
                //also checks whether there's already an incorrectAnswer with same value
                while(incorrectAnswer == a * b || answers.contains(incorrectAnswer)){
                    incorrectAnswer = rd.nextInt(144)+1;
                }
                answers.add(incorrectAnswer);
            }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    public void divisionQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+a;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);
        while(b % a != 0){
            a = rd.nextInt(10)+1;
            b = rd.nextInt(10)+a;
        }
        //Updates QuestionBoard
        questionText.setText(Integer.toString(b) + "/" + Integer.toString(a));
        for(int i = 0; i < 4; i++){
            //Selects where the right answer will be
            if(i == locationOfCorrectAnswer){
                answers.add(b / a);
            }else {
                incorrectAnswer = rd.nextInt(20)+1;

                //incorrectAnswer will be reevaluated if there's already an integer equal to it there
                //also checks whether there's already an incorrectAnswer with same value
                while(incorrectAnswer == b / a || answers.contains(incorrectAnswer)){
                    incorrectAnswer = rd.nextInt(20)+1;
                }
                answers.add(incorrectAnswer);
            }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    public void subtractQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(50)+1;
        int b = rd.nextInt(50)+a;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(b) + "-" + Integer.toString(a));
        for(int i = 0; i < 4; i++){
            //Selects where the right answer will be
            if(i == locationOfCorrectAnswer){
                answers.add(b - a);
            }else {
                incorrectAnswer = rd.nextInt(20)+1;

                //incorrectAnswer will be reevaluated if there's already an integer equal to it there
                //also checks whether there's already an incorrectAnswer with same value
                while(incorrectAnswer == b - a || answers.contains(incorrectAnswer)){
                    incorrectAnswer = rd.nextInt(50)+1;
                }
                answers.add(incorrectAnswer);
            }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
}
