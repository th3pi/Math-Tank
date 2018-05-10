package nyc.tanjim.mathshark;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Advanced extends AppCompatActivity {
    Button button0, button1, button2, button3;
    TextView questionText, scoreView;
    ImageButton menuButton;
    int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    ConstraintLayout bg;
    Animation buttonsInit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        questionText = findViewById(R.id.quiestionsView);
        scoreView = findViewById(R.id.scoreView);
        bg = findViewById(R.id.bg);
        buttonsInit = AnimationUtils.loadAnimation(this,R.anim.advanced_init);
        //Generate the starting question
        generateQuestion();
    }
    public void choose(View view){
        //if statement connects the tapped answer to the correct answer
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            numberOfQuestions++;
            generateQuestion();
        }else {
            scoreView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.correct_animation));
            numberOfQuestions++;
            if(locationOfCorrectAnswer == 0){
                button0.startAnimation(AnimationUtils.loadAnimation(this,R.anim.correct_animation));
            } else if (locationOfCorrectAnswer == 1){
                button1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.correct_animation));
            } else if (locationOfCorrectAnswer == 2){
                button2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.correct_animation));
            } else if (locationOfCorrectAnswer == 3){
                button3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.correct_animation));
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateQuestion();
                }
            }, 1000);
        }
        scoreView.setText(getString(R.string.advanced_score,score,numberOfQuestions));
        questionText.startAnimation(AnimationUtils.loadAnimation(this,R.anim.question_flicker));
        answers.clear();
    }
    public void generateQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(10);
        if(a == 0) {
            squareQuestions();
        }else if(a == 1){
            cubeQuestions();
        }else if(a == 2){
            sqrtQuestions();
        }else if(a == 3){
            cbrtQuestions();
        }else if(a == 4){
            tripleSumQuestions();
        }else if(a == 5){
            tripleSubtractQuestions();
        }else if(a == 6){
            tripleSubMultiplyQuestions();
        }else if(a == 7){
            tripleSumMultiplyQuestions();
        }else if(a == 8){
            tripleSubDivisionQuestions();
        }else if(a == 9){
            tripleSumDivisionQuestions();
        }
        answers.clear();
        button0.startAnimation(buttonsInit);
        button1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.advanced_init_1));
        button2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.advanced_init_2));
        button3.startAnimation(AnimationUtils.loadAnimation(this,R.anim.advanced_init_3));
    }
    public void squareQuestions(){
        Random rd = new Random();
        int[] arr = {2,3,4,5,6,7,8,9,10,11,12};
        int a = arr[rd.nextInt(11)];
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.square,a));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((int)Math.pow(a,2));
            }else{
                incorrectAnswer = rd.nextInt(80)+4;
                while(incorrectAnswer == (int)Math.pow(a,2)){
                    incorrectAnswer = rd.nextInt(80)+4;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void cubeQuestions(){
        Random rd = new Random();
        int[] arr = {2,3,4,5};
        int a = arr[rd.nextInt(4)];
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.cube,a));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((int)Math.pow(a,3));
            }else{
                incorrectAnswer = rd.nextInt(90)+30;
                while(incorrectAnswer == (int)Math.pow(a,3)){
                    incorrectAnswer = rd.nextInt(90)+30;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void sqrtQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(100)+1;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        while(Math.sqrt(a) % 1 != 0){
            a = rd.nextInt(100)+1;
        }
        questionText.setText(getString(R.string.sqrt,a));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((int)Math.sqrt(a));
            }else{
                incorrectAnswer = rd.nextInt(14)+2;
                while(incorrectAnswer == (int)Math.sqrt(a)){
                    incorrectAnswer = rd.nextInt(14)+2;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void cbrtQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(100)+1;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        while(Math.cbrt(a) % 1 != 0){
            a = rd.nextInt(100)+1;
        }
        questionText.setText(getString(R.string.cbrt,a));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((int)Math.cbrt(a));
            }else{
                incorrectAnswer = rd.nextInt(10)+2;
                while(incorrectAnswer == (int)Math.cbrt(a)){
                    incorrectAnswer = rd.nextInt(10)+2;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void tripleSumQuestions(){
        Random rd = new Random();
        int a = rd.nextInt(25)+20;
        int b = rd.nextInt(15)+10;
        int c = rd.nextInt(10)+5;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.triple_sum,a,b,c));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b+c);
            }else{
                incorrectAnswer = rd.nextInt(50)+35;
                while(incorrectAnswer == a+b+c){
                    incorrectAnswer = rd.nextInt(50)+35;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void tripleSubtractQuestions(){
        Random rd = new Random();
        int a = rd.nextInt((50-25)+1)+25;
        int b = rd.nextInt((15-10)+1)+10;
        int c = rd.nextInt((10-5)+1)+5;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.triple_sub,a,b,c));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a-b-c);
            }else{
                incorrectAnswer = rd.nextInt(25)+10;
                while(incorrectAnswer == a-b-c){
                    incorrectAnswer = rd.nextInt(25)+10;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void tripleSubMultiplyQuestions(){
        Random rd = new Random();
        int a = rd.nextInt((50-25)+1)+25;
        int b = rd.nextInt((15-10)+1)+10;
        int c = rd.nextInt((5-1)+1)+1;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.triple_sub_multiply,a,b,c));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((a-b)*c);
            }else{
                incorrectAnswer = rd.nextInt((80-20)+1)+20;
                while(incorrectAnswer == (a-b)*c){
                    incorrectAnswer = rd.nextInt((80-20)+1)+20;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void tripleSumMultiplyQuestions(){
        Random rd = new Random();
        int a = rd.nextInt((25-20)+1)+20;
        int b = rd.nextInt((15-10)+1)+10;
        int c = rd.nextInt((5-1)+1)+1;
        int incorrectAnswer;
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.triple_sum_multiply,a,b,c));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((a+b)*c);
            }else{
                incorrectAnswer = rd.nextInt((150-80)+1)+80;
                while(incorrectAnswer == (a+b)*c){
                    incorrectAnswer = rd.nextInt((150-80)+1)+80;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }

    public void tripleSubDivisionQuestions(){
        Random rd = new Random();
        int a = rd.nextInt((50-25)+1)+25;
        int b = rd.nextInt((15-10)+1)+10;
        int c = rd.nextInt((10-1)+1)+1;
        int incorrectAnswer;
        while((a-b) % c != 0){
             a = rd.nextInt((50-25)+1)+25;
             b = rd.nextInt((15-10)+1)+10;
             c = rd.nextInt((10-1)+1)+1;
        }
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.triple_sub_div,a,b,c));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((a-b)/c);
            }else{
                incorrectAnswer = rd.nextInt((40-15)+1)+15;
                while(incorrectAnswer == (a-b)/c){
                    incorrectAnswer = rd.nextInt((40-15)+1)+15;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
    }
    public void tripleSumDivisionQuestions(){
        Random rd = new Random();
        int a = rd.nextInt((25-20)+1)+20;
        int b = rd.nextInt((15-10)+1)+10;
        int c = rd.nextInt((10-1)+1)+1;
        int incorrectAnswer;
        while((a+b) % c != 0){
            a = rd.nextInt((50-25)+1)+25;
            b = rd.nextInt((15-10)+1)+10;
            c = rd.nextInt((10-1)+1)+1;
        }
        locationOfCorrectAnswer = rd.nextInt(4);
        questionText.setText(getString(R.string.triple_sum_div,a,b,c));
        for(int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add((a+b)/c);
            }else{
                incorrectAnswer = rd.nextInt((40-10)+1)+10;
                while(incorrectAnswer == (a+b)/c){
                    incorrectAnswer = rd.nextInt((40-10)+1)+10;
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(getString(R.string.box,answers.get(0)));
        button1.setText(getString(R.string.box,answers.get(1)));
        button2.setText(getString(R.string.box,answers.get(2)));
        button3.setText(getString(R.string.box,answers.get(3)));
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
