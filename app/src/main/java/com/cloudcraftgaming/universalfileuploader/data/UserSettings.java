package com.cloudcraftgaming.universalfileuploader.data;

import java.util.Map;

/**
 * Created by Nova Fox on 10/1/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class UserSettings {
    private final boolean privacyAgree;
    private final boolean wifiOnly;
    private final boolean copyToClipboard;

    public UserSettings(Map<String, String> rawSettings) {
        privacyAgree = Boolean.valueOf(rawSettings.get("privacyAgree"));
        wifiOnly = Boolean.valueOf(rawSettings.get("WifiOnly"));
        copyToClipboard = Boolean.valueOf(rawSettings.get("CopyClipboard"));
    }

    public UserSettings() {
        privacyAgree = false;
        wifiOnly = false;
        copyToClipboard = true;
    }

    //Getters/Settings
    public boolean getPrivacyAgree() {
        return privacyAgree;
    }

    public boolean getWifiOnly() {
        return wifiOnly;
    }

    public boolean getCopyToClipboard() {
        return copyToClipboard;
    }
}