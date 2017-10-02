package com.cloudcraftgaming.universalfileuploader.network.uploaders;

import android.content.Context;
import android.os.AsyncTask;
import com.cloudcraftgaming.universalfileuploader.handlers.AlertHandler;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import okhttp3.*;

import java.io.File;

/**
 * Created by Nova Fox on 10/1/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class NothingDomains extends AsyncTask<Object, Void, String> {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public NothingDomains() {
    }

    @Override
    protected String doInBackground(Object... params) {
        File file = (File) params[0];
        Context context = (Context) params[1];
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody partBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("files[]", "image.png", RequestBody.create(MEDIA_TYPE_PNG, file))
                    .build();

            Request request = new Request.Builder()
                    .header("Authorization", SettingsManager.getManager().getSettings().getNothingDomainsKey())
                    .url("https://nothing.domains/api/upload/pomf")
                    .post(partBody)
                    .build();

            System.out.println("Sending POST Request: " + request.toString());

            Response response = client.newCall(request).execute();

            //TODO: Handle response
            System.out.println("Response received: " + response.toString());
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            AlertHandler.uploadErrorAlert(context);
        }
        return "FAILURE";
    }

    @Override
    protected void onPostExecute(String response) {
        //TODO: Handle response
        System.out.println("Response: " + response);
    }
}