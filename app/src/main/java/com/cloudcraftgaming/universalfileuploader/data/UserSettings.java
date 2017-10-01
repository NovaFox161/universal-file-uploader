package com.cloudcraftgaming.universalfileuploader.data;

import java.util.Map;

/**
 * Created by Nova Fox on 10/1/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class UserSettings {
    private final boolean wifiOnly;

    private final String nothingDomainsKey;

    public UserSettings(Map<String, String> rawSettings) {
        wifiOnly = Boolean.valueOf(rawSettings.get("WifiOnly"));
        nothingDomainsKey = rawSettings.get("NothingDomainsKey");
    }

    public UserSettings() {
        wifiOnly = false;
        nothingDomainsKey = "";
    }

    //Getters/Settings
    public boolean getWifiOnly() {
        return wifiOnly;
    }

    public String getNothingDomainsKey() {
        return nothingDomainsKey;
    }
}