package nyc.tanjim.mathtank;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class scoredHow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scored_how);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView quickmath = findViewById(R.id.quickmath);
        TextView advanced = findViewById(R.id.advanced);
        TextView timetrials = findViewById(R.id.timetrials);
        TextView quickmathRules = findViewById(R.id.quickmathrules);
        TextView timetrialsRules = findViewById(R.id.timetrialsrules);
        TextView advancedRules = findViewById(R.id.advancedrules);
        boolean darkModePref = sharedPreferences.getBoolean(SettingsActivity.KEY_DARK_MODE_SWITCH,false);

        if(darkModePref){
            ScrollView scrollView = (findViewById(R.id.scoredHowBg));
            scrollView.setBackgroundColor(getResources().getColor(R.color.qboard_black));
            quickmath.setTextColor(getResources().getColor(R.color.white));
            advanced.setTextColor(getResources().getColor(R.color.white));
            timetrials.setTextColor(getResources().getColor(R.color.white));
            quickmathRules.setTextColor(getResources().getColor(R.color.white));
            timetrialsRules.setTextColor(getResources().getColor(R.color.white));
            advancedRules.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void close(View view){
        this.finish();
    }
}
