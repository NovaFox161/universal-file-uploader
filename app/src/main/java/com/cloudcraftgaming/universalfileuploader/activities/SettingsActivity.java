package com.cloudcraftgaming.universalfileuploader.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Switch;
import com.cloudcraftgaming.universalfileuploader.R;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {
    Switch uploadOnlyOnWifi;
    Switch copyToClipboard;

    EditText nothingDomainsKey;
    EditText nothingDomainsLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //Register objects
        uploadOnlyOnWifi = findViewById(R.id.wifi_only_upload);
        copyToClipboard = findViewById(R.id.copy_clipboard);
        nothingDomainsKey = findViewById(R.id.nothing_domains_key);
        nothingDomainsLink = findViewById(R.id.nothing_domains_link);

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
            settings.put("WifiOnly", uploadOnlyOnWifi.isChecked());
            settings.put("CopyClipboard", copyToClipboard.isChecked());
            settings.put("NothingDomainsKey", nothingDomainsKey.getText().toString());
            settings.put("NothingDomainsLink", nothingDomainsLink.getText().toString());

            SettingsManager.getManager().saveSettings(settings);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshDisplay() {
        uploadOnlyOnWifi.setChecked(SettingsManager.getManager().getSettings().getWifiOnly());
        copyToClipboard.setChecked(SettingsManager.getManager().getSettings().getCopyToClipboard());
        nothingDomainsKey.setText(SettingsManager.getManager().getSettings().getNothingDomainsKey());
        nothingDomainsLink.setText(SettingsManager.getManager().getSettings().getNothingDomainsLink());
    }
}