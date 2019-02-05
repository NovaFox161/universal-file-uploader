package com.cloudcraftgaming.universalfileuploader.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.Switch;

import com.cloudcraftgaming.universalfileuploader.R;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {
    CheckBox privacyAgree;

    Switch uploadOnlyOnWifi;
    Switch copyToClipboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //Register objects
        privacyAgree = findViewById(R.id.privacy_agree);
        uploadOnlyOnWifi = findViewById(R.id.wifi_only_upload);
        copyToClipboard = findViewById(R.id.copy_clipboard);

        refreshDisplay();
    }

    @Override
    protected void onPause() {
        saveSettings();
        super.onPause();
    }

    private void saveSettings() {
        try {

            JSONObject settings = new JSONObject();
            settings.put("privacyAgree", privacyAgree.isChecked());
            settings.put("WifiOnly", uploadOnlyOnWifi.isChecked());
            settings.put("CopyClipboard", copyToClipboard.isChecked());

            SettingsManager.getManager().saveSettings(settings);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshDisplay() {
        privacyAgree.setChecked(SettingsManager.getManager().getSettings().getPrivacyAgree());
        uploadOnlyOnWifi.setChecked(SettingsManager.getManager().getSettings().getWifiOnly());
        copyToClipboard.setChecked(SettingsManager.getManager().getSettings().getCopyToClipboard());
    }
}