package nyc.tanjim.mathshark;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Advanced extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    Button button0, button1, button2, button3;
    TextView questionText, scoreView;
    ImageButton menuButton;
    int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    ConstraintLayout bg;


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
        bg = findViewById(R.id.bg);
        menuButton = findViewById(R.id.menuButton);

        //Creates and inflates the top left pop up menu
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Advanced.this, v);
                popup.setOnMenuItemClickListener(Advanced.this);
                popup.inflate(R.menu.popup);
                popup.show();
            }
        });

        //Generate the starting question
        generateQuestion();
    }

    //Adds functionality to the pop up menu
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                return true;
            case R.id.reset:
                reset();
                return true;
            case R.id.exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            default:
                return false;
        }
    }

    //Answer button's functionality
    public void choose(View view){

        //gets the difference between number of questions answered and score
        double difference;
        difference = numberOfQuestions - score;

        //if statement connects the tapped answer to the correct answer
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            numberOfQuestions++;
            generateQuestion();
            bg.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.correct_gradient_1));
            scoreView.setTextColor(Color.WHITE);
            questionText.setTextColor(Color.WHITE);
            menuButton.setImageResource(R.drawable.ic_menu_white);
        }else {
            numberOfQuestions++;
            generateQuestion();
            bg.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.wrong_gradient_1));
            scoreView.setTextColor(Color.WHITE);
            questionText.setTextColor(Color.WHITE);
            menuButton.setImageResource(R.drawable.ic_menu_white);
        }
        scoreView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));


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
        }else{
            sumQuestions();
        }
        answers.clear();
    }

    public void sumQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(20)+1;
        int b = rd.nextInt(20)+1;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);

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
        int a = rd.nextInt(20)+1;
        int b = rd.nextInt(60)+a;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        while(b % a != 0 || b == a){
            a = rd.nextInt(10)+1;
            b = rd.nextInt(60)+a;
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
        locationOfCorrectAnswer = rd.nextInt(4);

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

    //Resets current session
    public void reset(){
        score = 0;
        numberOfQuestions = 0;
        scoreView.setText("0/0");
        generateQuestion();
        bg.setBackgroundColor(0xffffff);
        scoreView.setTextColor(Color.GRAY);
        questionText.setTextColor(Color.GRAY);
        menuButton.setImageResource(R.drawable.ic_menu);

    }


}
