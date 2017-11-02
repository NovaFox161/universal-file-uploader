package com.cloudcraftgaming.universalfileuploader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.cloudcraftgaming.universalfileuploader.activities.SettingsActivity;
import com.cloudcraftgaming.universalfileuploader.handlers.AlertHandler;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import com.cloudcraftgaming.universalfileuploader.network.Host;
import com.cloudcraftgaming.universalfileuploader.network.UploadManager;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;

import java.util.ArrayList;
import java.util.List;

public class UploadFileActivity extends AppCompatActivity {

    FilePicker filePicker;

    Intent file = null;

    Button fileSelect;
    Button uploadButton;

    Spinner selectedUploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SettingsManager.getManager().init(this);

        //Register banner ad
        PublisherAdView mPublisherAdView = findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);


        //Register file picker
        filePicker = new FilePicker(this);
        filePicker.setFilePickerCallback(new FilePickerCallback() {
            @Override
            public void onFilesChosen(List<ChosenFile> files) {
                // Display Files
            }

            @Override
            public void onError(String message) {
                // Handle errors
            }
        });

        //Register elements
        fileSelect = findViewById(R.id.select_file);
        uploadButton = findViewById(R.id.upload_button);
        selectedUploader = findViewById(R.id.selected_uploader);

        //Set upload selector list dynamically
        List<String> uploaders = new ArrayList<>();
        uploaders.add("Select Uploader");

        for (Host h : Host.values()) {
            uploaders.add(h.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, uploaders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedUploader.setAdapter(adapter);

        //Do button things
        fileSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SettingsManager.getManager().getSettings().getPrivacyAgree()) {
                    if (hasRuntimePermission()) {
                        filePicker.pickFile();
                    } else {
                        requestRuntimePermission();
                    }
                } else {
                    AlertHandler.noPrivacy(UploadFileActivity.this);
                }
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!SettingsManager.getManager().getSettings().getPrivacyAgree()) {
                    AlertHandler.noPrivacy(UploadFileActivity.this);
                    return;
                }
                if (file == null) {
                    AlertHandler.noFileAlert(UploadFileActivity.this);
                } else {
                    if (selectedUploader.getSelectedItemPosition() == 0) {
                        AlertHandler.noUploaderAlert(UploadFileActivity.this);
                    } else {
                        //Let the upload manager go from here.
                        UploadManager.getManager().handleUpload(UploadFileActivity.this, file, selectedUploader);
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.action_privacy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cloudcraftgaming.com/policy/privacy-app"));
            startActivity(browserIntent);
        } else if (id == R.id.action_support) {
            Intent browserIndent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/2TFqyuy"));
            startActivity(browserIndent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_FILE && resultCode == RESULT_OK) {
            filePicker.submit(data);
            fileSelect.setText(data.getData().getPath());
            file = data;
        }
    }

    private void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(UploadFileActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UploadFileActivity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private boolean hasRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(UploadFileActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
