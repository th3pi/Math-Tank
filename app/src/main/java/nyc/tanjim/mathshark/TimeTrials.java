package nyc.tanjim.mathshark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    ArrayList<String> questions = new ArrayList<String>();;

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


}
