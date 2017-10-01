package com.cloudcraftgaming.universalfileuploader.network;

import android.annotation.SuppressLint;
import android.content.Context;
import com.cloudcraftgaming.universalfileuploader.data.UserSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Nova Fox on 10/1/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class SettingsManager {
    @SuppressLint("StaticFieldLeak")
    private static final SettingsManager instance = new SettingsManager();

    private UserSettings settings;
    private Context context;

    public static SettingsManager getManager() {
        return instance;
    }

    private SettingsManager() {
    }

    public void init(Context _context) {
        context = _context;
    }

    //Getters
    public UserSettings getSettings() {
        if (settings == null) {
            readSettings();
        }
        return settings;
    }

    //Functions
    private void readSettings() {
        String FILENAME = "user.prefs";

        File file = context.getFileStreamPath(FILENAME);
        if (file.exists()) {

            try {

                FileInputStream fis = context.openFileInput(FILENAME);

                Map<String, String> rawSettings;

                Type type = new TypeToken<Map<String, String>>() {
                }.getType();

                FileReader fr = new FileReader(fis.getFD());

                rawSettings = new Gson().fromJson(fr, type);

                settings = new UserSettings(rawSettings);

                fr.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            settings = new UserSettings();
        }
    }

    public void saveSettings(JSONObject settingsObject) {
        String FILENAME = "user.prefs";

        try {

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(settingsObject.toString().getBytes());
            fos.close();

            readSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}