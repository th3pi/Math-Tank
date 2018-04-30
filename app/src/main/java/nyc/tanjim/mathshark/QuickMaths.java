package nyc.tanjim.mathshark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class QuickMaths extends AppCompatActivity {
    Button button0, button1, button2, button3;
    TextView questionText;
    ArrayList<Integer> sumAnswers = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_maths);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        questionText = findViewById(R.id.quiestionsView);

        generateQuestion();
    }
    public void choose(View view){
        generateQuestion();
        sumAnswers.clear();
    }

    public void generateQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+1;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(a) + "+" + Integer.toString(b));
        for(int i = 0; i < 4; i++){
        //Selects where the right answer will be
        if(i == locationOfCorrectAnswer){
            sumAnswers.add(a + b);
        }else {
            incorrectAnswer = rd.nextInt(20)+1;

            //incorrectAnswer will be reevaluated if there's already an integer equal to it there
            //also checks whether there's already an incorrectAnswer with same value
            while(incorrectAnswer == a + b || sumAnswers.contains(incorrectAnswer)){
                  incorrectAnswer = rd.nextInt(20)+1;
            }
            sumAnswers.add(incorrectAnswer);
          }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(sumAnswers.get(0)));
        button1.setText(Integer.toString(sumAnswers.get(1)));
        button2.setText(Integer.toString(sumAnswers.get(2)));
        button3.setText(Integer.toString(sumAnswers.get(3)));
    }

    public void multiplyQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+1;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(a) + "x" + Integer.toString(b));
        for(int i = 0; i < 4; i++){
            //Selects where the right answer will be
            if(i == locationOfCorrectAnswer){
                sumAnswers.add(a * b);
            }else {
                incorrectAnswer = rd.nextInt(20)+1;

                //incorrectAnswer will be reevaluated if there's already an integer equal to it there
                //also checks whether there's already an incorrectAnswer with same value
                while(incorrectAnswer == a * b || sumAnswers.contains(incorrectAnswer)){
                    incorrectAnswer = rd.nextInt(20)+1;
                }
                sumAnswers.add(incorrectAnswer);
            }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(sumAnswers.get(0)));
        button1.setText(Integer.toString(sumAnswers.get(1)));
        button2.setText(Integer.toString(sumAnswers.get(2)));
        button3.setText(Integer.toString(sumAnswers.get(3)));
    }
    public void divisionQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+1;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(a) + "/" + Integer.toString(b));
        for(int i = 0; i < 4; i++){
            //Selects where the right answer will be
            if(i == locationOfCorrectAnswer){
                sumAnswers.add(a / b);
            }else {
                incorrectAnswer = rd.nextInt(20)+1;

                //incorrectAnswer will be reevaluated if there's already an integer equal to it there
                //also checks whether there's already an incorrectAnswer with same value
                while(incorrectAnswer == a / b || sumAnswers.contains(incorrectAnswer)){
                    incorrectAnswer = rd.nextInt(20)+1;
                }
                sumAnswers.add(incorrectAnswer);
            }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(sumAnswers.get(0)));
        button1.setText(Integer.toString(sumAnswers.get(1)));
        button2.setText(Integer.toString(sumAnswers.get(2)));
        button3.setText(Integer.toString(sumAnswers.get(3)));
    }
    public void subtractQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(10)+1;
        int b = rd.nextInt(10)+1;
        int incorrectAnswer;
        int locationOfCorrectAnswer = rd.nextInt(4);

        //Updates QuestionBoard
        questionText.setText(Integer.toString(a) + "-" + Integer.toString(b));
        for(int i = 0; i < 4; i++){
            //Selects where the right answer will be
            if(i == locationOfCorrectAnswer){
                sumAnswers.add(a - b);
            }else {
                incorrectAnswer = rd.nextInt(20)+1;

                //incorrectAnswer will be reevaluated if there's already an integer equal to it there
                //also checks whether there's already an incorrectAnswer with same value
                while(incorrectAnswer == a - b || sumAnswers.contains(incorrectAnswer)){
                    incorrectAnswer = rd.nextInt(20)+1;
                }
                sumAnswers.add(incorrectAnswer);
            }
        }

        //Updates the button text with incorrectAnswers
        button0.setText(Integer.toString(sumAnswers.get(0)));
        button1.setText(Integer.toString(sumAnswers.get(1)));
        button2.setText(Integer.toString(sumAnswers.get(2)));
        button3.setText(Integer.toString(sumAnswers.get(3)));
    }

}
