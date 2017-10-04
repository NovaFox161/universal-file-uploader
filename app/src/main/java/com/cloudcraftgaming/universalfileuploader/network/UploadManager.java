package com.cloudcraftgaming.universalfileuploader.network;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import com.cloudcraftgaming.universalfileuploader.network.uploaders.PomfUploader;

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
        Host host = Host.fromName(selectedUploader.getSelectedItem().toString());
        assert host != null;
        if (host.getType() == HostType.POMF) {
            new PomfUploader(host).execute(file, context);
        }
    }

    public void finishUpload(String fileUrl, Host host, Context source) {
        String completeUrl = fileUrl;
        if (host == Host.NOTHING_DOMAINS) {
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