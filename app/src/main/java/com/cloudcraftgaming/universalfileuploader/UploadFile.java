package com.cloudcraftgaming.universalfileuploader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import com.cloudcraftgaming.universalfileuploader.network.UploadManager;
import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;

import java.util.List;

public class UploadFile extends AppCompatActivity {
    FilePicker filePicker;

    Button fileSelect;
    Button uploadButton;

    Spinner selectedUploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                filePicker.pickFile();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedUploader.getSelectedItemPosition() == 0) {
                    //TODO: Add alert dialog for no uploader selected
                } else {
                    //Let the upload manager go from here.
                    UploadManager.getManager().handleUpload(filePicker, selectedUploader);
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
            //TODO: Make settings to handle Nothing Domains Auth key and other settings!!
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_FILE && resultCode == RESULT_OK) {
            filePicker.submit(data);
            fileSelect.setText(data.getDataString());
        }
    }
}
