package com.cloudcraftgaming.universalfileuploader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import com.cloudcraftgaming.universalfileuploader.activities.SettingsActivity;
import com.cloudcraftgaming.universalfileuploader.handlers.AlertHandler;
import com.cloudcraftgaming.universalfileuploader.handlers.SettingsManager;
import com.cloudcraftgaming.universalfileuploader.network.UploadManager;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;

import java.util.List;

public class UploadFile extends AppCompatActivity {
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

        filePicker = new FilePicker(this);

        // filePicker.allowMultiple();
        // filePicker.
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

        //Do button things
        fileSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestRuntimePermission();
                filePicker.pickFile();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (file == null) {
                    AlertHandler.noFileAlert(UploadFile.this);
                } else {
                    if (selectedUploader.getSelectedItemPosition() == 0) {
                        AlertHandler.noUploaderAlert(UploadFile.this);
                    } else {
                        //Let the upload manager go from here.
                        UploadManager.getManager().handleUpload(UploadFile.this, file, selectedUploader);
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
        }

        return super.onOptionsItemSelected(item);
    }

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
            if (ContextCompat.checkSelfPermission(UploadFile.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UploadFile.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
}
