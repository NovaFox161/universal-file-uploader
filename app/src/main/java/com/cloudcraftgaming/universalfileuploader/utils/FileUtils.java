package com.cloudcraftgaming.universalfileuploader.utils;

import android.content.Intent;
import com.cloudcraftgaming.universalfileuploader.network.Host;

import java.io.File;

/**
 * Created by Nova Fox on 10/4/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class FileUtils {
    @SuppressWarnings("ConstantConditions")
    public static boolean isTooLarge(Intent fileIntent, Host host) {
        File file = new File(fileIntent.getData().getPath());
        long sizeInMb = file.length() / (1024 * 1024);
        return sizeInMb > host.getSizeLimitMb();
    }
}