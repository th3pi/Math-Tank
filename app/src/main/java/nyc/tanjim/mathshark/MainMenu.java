package nyc.tanjim.mathshark;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            backgroundAnimation();
        }
    }

    public void openAdvanced(View view){
        Intent intent = new Intent(getApplicationContext(), Advanced.class);
        startActivity(intent);
    }
    public void openQuickMaths(View view){
        startActivity(new Intent(this,QuickMathLoadingScreen.class));
    }
    public void openTimeTrials(View view){
        startActivity(new Intent(this, TimeTrials.class));
    }

    public void backgroundAnimation(){
            ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
            constraintLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.animation));
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(5000);
            animationDrawable.setExitFadeDuration(6000);
            animationDrawable.start();
    }

}
