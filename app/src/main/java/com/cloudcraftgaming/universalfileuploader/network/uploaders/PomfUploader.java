package com.cloudcraftgaming.universalfileuploader.network.uploaders;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.cloudcraftgaming.universalfileuploader.network.Host;
import com.cloudcraftgaming.universalfileuploader.network.UploadManager;
import com.cloudcraftgaming.universalfileuploader.utils.AuthKey;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Nova Fox on 10/3/17.
 * Website: www.cloudcraftgaming.com
 * For Project: universal-file-uploader
 */

public class PomfUploader extends AsyncTask<Object, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Context source;
    private final Host host;

    public PomfUploader(Host _host) {
        host = _host;
    }

    @Override
    protected String doInBackground(Object... params) {
        String result;
        String tag = "UFU";
        try {
            //Get everything we need ahead of time.
            Intent fileIntent = (Intent) params[0];
            source = (Context) params[1];

            //Uri fileUri = fileIntent.getParcelableExtra(Intent.EXTRA_STREAM);
            Uri fileUri = fileIntent.getData();

            ContentResolver cr = source.getContentResolver();
            assert fileUri != null;
            ParcelFileDescriptor file = cr.openFileDescriptor(fileUri, "r");
            String fileName = fileUri.getLastPathSegment();
            String contentType = cr.getType(fileUri);
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType);

            //do POST request
            HttpURLConnection conn = (HttpURLConnection) new URL(host.getUrl()).openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");

            conn.addRequestProperty("Authorization", AuthKey.getAuthKey(host));
            conn.setRequestProperty("Connection", "Keep-Alive");
            String boundary = "*****";
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes("--" + boundary + "\r\n");
            out.writeBytes(String.format("Content-Disposition: form-data; name=\"%s\";filename=\"%s.%s\"\r\nContent-type: %s\r\n",
                    host.getFieldName(), fileName, extension, contentType));
            out.writeBytes("\r\n");

            Log.d(tag, fileName + "." + extension);

            assert file != null;
            FileInputStream fileInputStream = new FileInputStream(file.getFileDescriptor());

            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 1024 * 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            Log.d(tag, "Pre-read file " + fileInputStream);
            // Read file
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                out.write(buffer, 0, bytesRead);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            Log.d(tag, "Post-read file");
            out.writeBytes("\r\n");
            out.writeBytes("--" + boundary + "--\r\n");

            //Response from the server (code and message)
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            InputStream _is;
            if (conn.getResponseCode() / 100 == 2) { // 2xx code means success
                _is = conn.getInputStream();
            } else { //Any other error code
                _is = conn.getErrorStream();
            }

            Scanner reader = new Scanner(_is);
            result = reader.nextLine();

            Log.d(tag, String.format("%d: %s", responseCode, responseMessage));
            Log.d(tag, "Result: " + result);

            fileInputStream.close();
            reader.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            Log.e(tag, e.getMessage());
            e.printStackTrace();
            return String.format("Upload Failed, check your internet connection (%s)", e.getMessage());
        }
        return extractUrl(result);
    }

    private String extractUrl(String result) {
        try {
            JSONObject jsonMain = new JSONObject(result);
            JSONArray jsonFiles = jsonMain.getJSONArray("files");
            JSONObject fileJson = jsonFiles.getJSONObject(0);
            return fileJson.getString("url");
        } catch (StringIndexOutOfBoundsException e) {
            //No need to print error info, we already know what this is caused by
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        UploadManager.getManager().finishUpload(response, host, source);
    }
}