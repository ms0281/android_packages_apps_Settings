package com.android.settings.security;

import android.os.Bundle;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;
import com.android.settings.security.BatteryStatsPreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import android.content.Context;
import android.app.settings.SettingsEnums;
import android.provider.Settings;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@SearchIndexable
public class LockscreenChargingSettingsFragment extends SettingsPreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.lock_screen_charging_settings, rootKey);
        
        List<AbstractPreferenceController> controllers = createPreferenceControllers(getContext());
        for (AbstractPreferenceController controller : controllers) {
            controller.displayPreference(getPreferenceScreen());
        }
    }
    
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new BatteryStatsPreferenceController(context, "lockscreen_battery_stats"));
        controllers.add(new BatteryStatsPreferenceController(context, "lockscreen_battery_stats_ma_only"));
        return controllers;
    }
    
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.lock_screen_charging_settings);
    
    @Override
    public int getMetricsCategory() {
    return SettingsEnums.SECURITY;
    }
}
