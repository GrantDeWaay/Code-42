package edu.iastate.code42;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import edu.iastate.code42.databinding.ActivitySettingsBinding;
import edu.iastate.code42.utils.BaseDrawer;

/**
 * Settings screen using Preferences xml and Shared Preferences, based on example Settings Activity
 * Layout: activity_settings
 * Extends BaseDrawer
 * @author Andrew
 */
public class SettingsActivity extends BaseDrawer {
    ActivitySettingsBinding activityBaseDrawerBinding;

    SharedPreferences appSetting;
    SharedPreferences.Editor settingEditor;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBaseDrawerBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activityBaseDrawerBinding.getRoot());
        allocateActivityTitle("Settings");

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        appSetting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        settingEditor = appSetting.edit();

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals("theme")){
                    if(appSetting.getString("theme", "").equals(getString(R.string.theme_options_1))){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }else if(appSetting.getString("theme", "").trim().equals(getString(R.string.theme_options_2))){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }else{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    }
                }

            }
        };
        appSetting.registerOnSharedPreferenceChangeListener(listener);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preference, rootKey);
        }
    }
}