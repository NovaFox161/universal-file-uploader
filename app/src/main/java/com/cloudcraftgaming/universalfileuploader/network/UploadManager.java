package com.cloudcraftgaming.universalfileuploader.network;

import android.content.Intent;
import android.widget.Spinner;
import com.kbeanie.multipicker.api.FilePicker;

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

    public boolean handleUpload(FilePicker filePicker, Intent file, Spinner selectedUploader) {
        if (selectedUploader.getSelectedItemPosition() == 1) {
            //TODO: Handle upload to nothing domains
        }
        return false;
    }
}
