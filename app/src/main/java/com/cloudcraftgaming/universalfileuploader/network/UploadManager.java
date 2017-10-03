package com.cloudcraftgaming.universalfileuploader.network;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import com.cloudcraftgaming.universalfileuploader.network.uploaders.NothingDomains;

/**
 * Created by Nova Fox on 9/30/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class UploadManager {
    private static final UploadManager instance = new UploadManager();

    public static UploadManager getManager() {
        return instance;
    }

    private UploadManager() {
    }

    public void handleUpload(Context context, Intent file, Spinner selectedUploader) {
        if (selectedUploader.getSelectedItemPosition() == 1) {
            new NothingDomains().execute(file, context);
        }
    }

    public void finishUpload(String fileUrl, String host, Context source) {
        String completeUrl = "";
        if (host.equalsIgnoreCase("NothingDomains")) {
            String baseUrl = SettingsManager.getManager().getSettings().getNothingDomainsLink();
            completeUrl = baseUrl + fileUrl;
        }

        if (SettingsManager.getManager().getSettings().getCopyToClipboard()) {
            copyToClipboard(completeUrl, source);
        }
    }

    private void copyToClipboard(String url, Context source) {
        ClipboardManager clipboard = (ClipboardManager) source.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Upload URL", url);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toast t = Toast.makeText(source.getApplicationContext(), "Copied URL!", Toast.LENGTH_SHORT);
        t.show();
    }
}