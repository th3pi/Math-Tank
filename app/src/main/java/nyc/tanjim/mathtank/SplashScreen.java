package nyc.tanjim.mathtank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Intent i = new Intent(SplashScreen.this,MainMenu.class);
        Thread welcomeThread = new Thread() {
            @Override
            public void run(){
                try{
                    super.run();
                    sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
