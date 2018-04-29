package nyc.tanjim.mathshark;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            backgroundAnimation();
        }
    }

    public void backgroundAnimation(){
            ConstraintLayout constraintLayout = (findViewById(R.id.mainMenu));
            constraintLayout.setBackground(getResources().getDrawable(R.drawable.animation));
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(5000);
            animationDrawable.setExitFadeDuration(6000);
            animationDrawable.start();
    }
}
