package nyc.tanjim.mathtank;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;



public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String KEY_DARK_MODE_SWITCH = "dark_mode_switch";
    public static final String KEY_KIDS_MODE_SWITCH = "kids_mode_switch";
    public static final String KEY_ADDITION_ONLY_QUICKMATH = "addition_only_quickmath";
    public static final String KEY_SUBTRACTION_ONLY_QUICKMATH = "subtraction_only_quickmath";
    public static final String KEY_MULTIPLICATION_ONLY_QUICKMATH = "multiplication_only_quickmath";
    public static final String KEY_DIVISION_ONLY_QUICKMATH = "division_only_quickmath";
    public static final String KEY_TIMER = "timer_quick_math";
    public static final String KEY_ADDITION_ONLY_TIMETRIALS = "addition_only_timetrials";
    public static final String KEY_SUBTRACTION_ONLY_TIMETRIALS = "subtraction_only_timetrials";
    public static final String KEY_MULTIPLICATION_ONLY_TIMETRIALS = "multiplication_only_timetrials";
    public static final String KEY_DIVISION_ONLY_TIMETRIALS = "division_only_timetrials";
    public static final String KEY_TIMER_TIMETRIALS = "timer_timetrials";
    public static final String KEY_ADDITION_ADVANCED ="addition_advanced";
    public static final String KEY_SUBTRACTION_ADVANCED ="subtraction_advanced";
    public static final String KEY_ADDITION_X_MULTIPLICATION ="addition_x_multiplication";
    public static final String KEY_SUBTRACTION_X_MULTIPLICATION ="subtraction_x_multiplication";
    public static final String KEY_SUBTRACTION_BY_DIVISION ="subtraction_by_division";
    public static final String KEY_ADDITION_BY_DIVISION ="addition_by_division";
    public static final String KEY_SQUARE_ROOT="sqrt";
    public static final String KEY_SQUARE="sqr";
    public static final String KEY_CUBE="cube";
    public static final String KEY_MUTE_MUSIC="mute_switch";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppThemeWActionBar);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(KEY_DARK_MODE_SWITCH)){
            if(sharedPreferences.getBoolean(KEY_DARK_MODE_SWITCH,false)){
                Toast.makeText(SettingsActivity.this,"Dark mode Activated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(SettingsActivity.this,"Dark mode Deactivated",Toast.LENGTH_SHORT).show();
            }
        }

        if(key.equals(KEY_KIDS_MODE_SWITCH)){
            if(sharedPreferences.getBoolean(KEY_KIDS_MODE_SWITCH,false)){
                Toast.makeText(SettingsActivity.this,"Kids mode Activated (QuickMaths only, others coming soon)",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(SettingsActivity.this,"Kids mode Deactivated",Toast.LENGTH_SHORT).show();
            }
        }

        if(!sharedPreferences.getBoolean(KEY_ADDITION_ONLY_QUICKMATH,false) && !sharedPreferences.getBoolean(KEY_SUBTRACTION_ONLY_QUICKMATH,false) && !sharedPreferences.getBoolean(KEY_MULTIPLICATION_ONLY_QUICKMATH,false) && !sharedPreferences.getBoolean(KEY_DIVISION_ONLY_QUICKMATH,false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_ADDITION_ONLY_QUICKMATH,true);
            editor.apply();
            Toast.makeText(SettingsActivity.this,"Please keep at least one type enabled",Toast.LENGTH_SHORT).show();
        }
        if(!sharedPreferences.getBoolean(KEY_ADDITION_ONLY_TIMETRIALS,false) && !sharedPreferences.getBoolean(KEY_SUBTRACTION_ONLY_TIMETRIALS,false) && !sharedPreferences.getBoolean(KEY_MULTIPLICATION_ONLY_TIMETRIALS,false) && !sharedPreferences.getBoolean(KEY_DIVISION_ONLY_TIMETRIALS,false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_ADDITION_ONLY_TIMETRIALS,true);
            editor.apply();
            Toast.makeText(SettingsActivity.this,"Please keep at least one type enabled",Toast.LENGTH_SHORT).show();
        }
        if(!sharedPreferences.getBoolean(KEY_ADDITION_ADVANCED,false)
                && !sharedPreferences.getBoolean(KEY_SUBTRACTION_ADVANCED,false)
                && !sharedPreferences.getBoolean(KEY_ADDITION_X_MULTIPLICATION,false)
                && !sharedPreferences.getBoolean(KEY_SUBTRACTION_X_MULTIPLICATION,false)
                && !sharedPreferences.getBoolean(KEY_ADDITION_BY_DIVISION,false)
                && !sharedPreferences.getBoolean(KEY_SUBTRACTION_BY_DIVISION,false)
                && !sharedPreferences.getBoolean(KEY_SQUARE,false)
                && !sharedPreferences.getBoolean(KEY_SQUARE_ROOT,false)
                && !sharedPreferences.getBoolean(KEY_CUBE,false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_ADDITION_ADVANCED,true);
            editor.apply();
            Toast.makeText(SettingsActivity.this,"Please keep at least one type enabled",Toast.LENGTH_SHORT).show();
        }
    }

}
