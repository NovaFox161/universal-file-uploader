package com.cloudcraftgaming.universalfileuploader.network;

import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;
import com.cloudcraftgaming.universalfileuploader.network.uploaders.NothingDomains;

import java.io.File;

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

    public boolean handleUpload(Context context, Intent file, Spinner selectedUploader) {
        if (selectedUploader.getSelectedItemPosition() == 1) {
            //TODO: Handle upload to nothing domains
            new NothingDomains().execute(new File(file.getData().getPath()), context);
        }
        return false;
    }
}