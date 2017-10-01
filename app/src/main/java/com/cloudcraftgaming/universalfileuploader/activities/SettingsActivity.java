package com.cloudcraftgaming.universalfileuploader.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import com.cloudcraftgaming.universalfileuploader.R;
import com.cloudcraftgaming.universalfileuploader.network.SettingsManager;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {
    Switch uploadOnlyOnWifi;

    EditText nothingDomainsKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //Register objects
        uploadOnlyOnWifi = findViewById(R.id.wifi_only_upload);
        nothingDomainsKey = findViewById(R.id.nothing_domains_key);

        refreshDisplay();

        final Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveSettings();
                finish();
            }
        });
    }

    private void saveSettings() {
        try {

            JSONObject settings = new JSONObject();
            settings.put("WifiOnly", uploadOnlyOnWifi.isChecked());
            settings.put("NothingDomainsKey", nothingDomainsKey.getText().toString());

            SettingsManager.getManager().saveSettings(settings);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshDisplay() {
        uploadOnlyOnWifi.setChecked(SettingsManager.getManager().getSettings().getWifiOnly());
        nothingDomainsKey.setText(SettingsManager.getManager().getSettings().getNothingDomainsKey());
    }
}