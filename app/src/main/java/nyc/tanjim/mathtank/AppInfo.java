package nyc.tanjim.mathtank;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.URL;



public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }
    public void donate(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/TanjimulB"));
        startActivity(browserIntent);
    }
}
