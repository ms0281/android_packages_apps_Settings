package com.android.settings.security;

import android.content.Context;
import android.provider.Settings;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.List;
import java.util.ArrayList;
import android.util.Log;

public class BatteryStatsPreferenceController extends AbstractPreferenceController implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private final String mPreferenceKey;

    public BatteryStatsPreferenceController(Context context, String preferenceKey) {
        super(context);
        mPreferenceKey = preferenceKey;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return mPreferenceKey;
    }

    @Override
    public void updateState(Preference preference) {
        final boolean enabled = Settings.Secure.getIntForUser(
                mContext.getContentResolver(), mPreferenceKey, 0, android.os.UserHandle.USER_CURRENT) == 1;
        if (preference instanceof SwitchPreferenceCompat) {
            ((SwitchPreferenceCompat) preference).setChecked(enabled);
        } else {
            Log.w("BatteryStatsController", "Preference ist nicht vom Typ SwitchPreferenceCompat: " + preference);
        }
    }
    
    public boolean isChecked() {
        if ("lockscreen_battery_stats".equals(mPreferenceKey)) {
            return Settings.Secure.getIntForUser(mContext.getContentResolver(), "lockscreen_battery_stats", 0, android.os.UserHandle.USER_CURRENT) == 1;
        } else if ("lockscreen_battery_stats_ma_only".equals(mPreferenceKey)) {
            return Settings.Secure.getIntForUser(mContext.getContentResolver(), "lockscreen_battery_stats_ma_only", 0, android.os.UserHandle.USER_CURRENT) == 1;
        }
        return false;
    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
    boolean enabled = (Boolean) newValue;
    int userId = UserHandle.myUserId();
    Log.d("BatteryStatsPrefCtrl", "onPreferenceChange triggered: key=" + preference.getKey() + ", newValue=" + newValue);

    if ("lockscreen_battery_stats".equals(preference.getKey())) {
        Settings.Secure.putIntForUser(
            mContext.getContentResolver(),
            "lockscreen_battery_stats",
            enabled ? 1 : 0,
            userId
        );
        
        int confirm = Settings.Secure.getIntForUser(
            mContext.getContentResolver(),
            "lockscreen_battery_stats",
            -1,
            userId
        );
        Log.d("BatteryStatsPrefCtrl", "Confirmed value for lockscreen_battery_stats: " + confirm);
        
        return true;
    } else if ("lockscreen_battery_stats_ma_only".equals(preference.getKey())) {
        Settings.Secure.putIntForUser(
            mContext.getContentResolver(),
            "lockscreen_battery_stats_ma_only",
            enabled ? 1 : 0,
            userId
        );
        
        int confirm = Settings.Secure.getIntForUser(
            mContext.getContentResolver(),
            "lockscreen_battery_stats_ma_only",
            -1,
            userId
        );
        Log.d("BatteryStatsPrefCtrl", "Confirmed value for lockscreen_battery_stats_ma_only: " + confirm);
        
        return true;
    }

    return false;
    }
    
    @Override
    public void displayPreference(PreferenceScreen screen) {
       super.displayPreference(screen);
       Preference pref = screen.findPreference(mPreferenceKey);
       if (pref != null) {
           pref.setOnPreferenceChangeListener(this);
           updateState(pref);
       }
    }
}
