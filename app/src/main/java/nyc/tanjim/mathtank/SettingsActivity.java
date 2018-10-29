package nyc.tanjim.mathtank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


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
    public static final String KEY_FLASHING_TEXT="flashing_text";
    public static boolean kidsModeNotDisabled = false;
    public static boolean timerDisabled = false;
    public static boolean typeDisabled = false;
    public static boolean rankingDisabled = false;
    private Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean(KEY_DARK_MODE_SWITCH,false)){
            setTheme(R.style.AppThemeWActionBarDark);
        }else{
            setTheme(R.style.AppThemeWActionBar);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(typeDisabled && kidsModeNotDisabled && timerDisabled){
            rankingDisabled = true;
            sharedPreferences.edit().putBoolean("ranking",rankingDisabled).apply();
        }else{
            rankingDisabled = false;
            sharedPreferences.edit().putBoolean("ranking",rankingDisabled).apply();
        }

        if(key.equals(KEY_DARK_MODE_SWITCH)){
            if(sharedPreferences.getBoolean(KEY_DARK_MODE_SWITCH,false)){
                Toast.makeText(getApplicationContext(),"Dark mode Activated",Toast.LENGTH_LONG).show();
                recreate();
            }else{
                Toast.makeText(getApplicationContext(),"Dark mode Deactivated",Toast.LENGTH_SHORT).show();
                recreate();
            }
        }

        if(key.equals(KEY_KIDS_MODE_SWITCH)){
            if(sharedPreferences.getBoolean(KEY_KIDS_MODE_SWITCH,false)){
                kidsModeNotDisabled = true;
                Toasty.warning(getApplicationContext(), "Score ranking disabled: Cannot rank when kids mode is enabled.", Toast.LENGTH_SHORT,true).show();
            }else{
                kidsModeNotDisabled = false;
                if(!timerDisabled && !typeDisabled) {
                    Toasty.success(getApplicationContext(), "Score ranking has been re-enabled.", Toast.LENGTH_SHORT, true).show();
                }
            }
        }

        if(!sharedPreferences.getBoolean(KEY_ADDITION_ONLY_QUICKMATH,false) && !sharedPreferences.getBoolean(KEY_SUBTRACTION_ONLY_QUICKMATH,false) && !sharedPreferences.getBoolean(KEY_MULTIPLICATION_ONLY_QUICKMATH,false) && !sharedPreferences.getBoolean(KEY_DIVISION_ONLY_QUICKMATH,false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_ADDITION_ONLY_QUICKMATH,true);
            editor.apply();
            Toast.makeText(getApplicationContext(),"Please keep at least one type enabled",Toast.LENGTH_SHORT).show();
        }
        if(!sharedPreferences.getBoolean(KEY_ADDITION_ONLY_TIMETRIALS,false) && !sharedPreferences.getBoolean(KEY_SUBTRACTION_ONLY_TIMETRIALS,false) && !sharedPreferences.getBoolean(KEY_MULTIPLICATION_ONLY_TIMETRIALS,false) && !sharedPreferences.getBoolean(KEY_DIVISION_ONLY_TIMETRIALS,false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_ADDITION_ONLY_TIMETRIALS,true);
            editor.apply();
            Toast.makeText(getApplicationContext(),"Please keep at least one type enabled",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"Please keep at least one type enabled",Toast.LENGTH_SHORT).show();
        }
        if(key.equals(KEY_ADDITION_ONLY_QUICKMATH) || key.equals(KEY_SUBTRACTION_ONLY_QUICKMATH) || key.equals(KEY_MULTIPLICATION_ONLY_QUICKMATH) || key.equals(KEY_DIVISION_ONLY_QUICKMATH)) {
            if (!sharedPreferences.getBoolean(KEY_ADDITION_ONLY_QUICKMATH, false) || !sharedPreferences.getBoolean(KEY_SUBTRACTION_ONLY_QUICKMATH, false) || !sharedPreferences.getBoolean(KEY_MULTIPLICATION_ONLY_QUICKMATH, false) || !sharedPreferences.getBoolean(KEY_DIVISION_ONLY_QUICKMATH, false)) {
                typeDisabled = true;
            }else{
                typeDisabled = false;
                if(!kidsModeNotDisabled && !timerDisabled) {
                    Toasty.success(getApplicationContext(), "Score ranking has been re-enabled.", Toast.LENGTH_SHORT, true).show();
                }
            }
        }
        if(key.equals(KEY_ADDITION_ONLY_TIMETRIALS) || key.equals(KEY_SUBTRACTION_ONLY_TIMETRIALS) || key.equals(KEY_MULTIPLICATION_ONLY_TIMETRIALS) || key.equals(KEY_DIVISION_ONLY_TIMETRIALS)) {
            if (!sharedPreferences.getBoolean(KEY_ADDITION_ONLY_TIMETRIALS, false) || !sharedPreferences.getBoolean(KEY_SUBTRACTION_ONLY_TIMETRIALS, false) || !sharedPreferences.getBoolean(KEY_MULTIPLICATION_ONLY_TIMETRIALS, false) || !sharedPreferences.getBoolean(KEY_DIVISION_ONLY_TIMETRIALS, false)) {
                typeDisabled = true;
            }else{
                typeDisabled = false;
                if(!kidsModeNotDisabled && !timerDisabled) {
                    Toasty.success(getApplicationContext(), "Score ranking has been re-enabled.", Toast.LENGTH_SHORT, true).show();
                }

            }
        }
        if(key.equals(KEY_ADDITION_ADVANCED)
                || key.equals(KEY_SUBTRACTION_ADVANCED)
                || key.equals(KEY_ADDITION_X_MULTIPLICATION)
                || key.equals(KEY_SUBTRACTION_X_MULTIPLICATION)
                || key.equals(KEY_ADDITION_BY_DIVISION)
                || key.equals(KEY_SUBTRACTION_BY_DIVISION)
                || key.equals(KEY_SQUARE)
                || key.equals(KEY_SQUARE_ROOT)
                || key.equals(KEY_CUBE)){
            if(!sharedPreferences.getBoolean(KEY_ADDITION_ADVANCED,false)
                    || !sharedPreferences.getBoolean(KEY_SUBTRACTION_ADVANCED,false)
                    || !sharedPreferences.getBoolean(KEY_ADDITION_X_MULTIPLICATION,false)
                    || !sharedPreferences.getBoolean(KEY_SUBTRACTION_X_MULTIPLICATION,false)
                    || !sharedPreferences.getBoolean(KEY_ADDITION_BY_DIVISION,false)
                    || !sharedPreferences.getBoolean(KEY_SUBTRACTION_BY_DIVISION,false)
                    || !sharedPreferences.getBoolean(KEY_SQUARE,false)
                    || !sharedPreferences.getBoolean(KEY_SQUARE_ROOT,false)
                    || !sharedPreferences.getBoolean(KEY_CUBE,false)){
                typeDisabled = true;
            }else{
                typeDisabled = false;
                if(!kidsModeNotDisabled && !timerDisabled) {
                    Toasty.success(getApplicationContext(), "Score ranking has been re-enabled.", Toast.LENGTH_SHORT, true).show();
                }

            }
        }
        
        if(key.equals(KEY_TIMER) || key.equals(KEY_TIMER_TIMETRIALS)){
            if(!sharedPreferences.getBoolean(KEY_TIMER,false) || !sharedPreferences.getBoolean(KEY_TIMER_TIMETRIALS,false)){
                timerDisabled = true;
            }else{
                timerDisabled = false;
                if(!kidsModeNotDisabled && !typeDisabled) {
                    Toasty.success(getApplicationContext(), "Score ranking has been re-enabled.", Toast.LENGTH_SHORT, true).show();
                }
            }
        }
    }
}
