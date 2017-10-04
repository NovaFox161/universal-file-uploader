package com.cloudcraftgaming.universalfileuploader.utils;

import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import com.cloudcraftgaming.universalfileuploader.network.Host;

/**
 * Created by Nova Fox on 10/4/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class AuthKey {
    public static String getAuthKey(Host host) {
        if (host == Host.NOTHING_DOMAINS) {
            return SettingsManager.getManager().getSettings().getNothingDomainsKey();
        } else {
            return "";
        }
    }
}