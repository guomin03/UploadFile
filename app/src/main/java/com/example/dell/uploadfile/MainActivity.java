package com.example.dell.uploadfile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.7.152:8080/UploadFile/Upload";
                File file = Environment.getExternalStorageDirectory();
                File fileAbs = new File(file,"a4.png");
                String fileName = fileAbs.getAbsolutePath();
                UploadThread thread = new UploadThread(url,fileName);
                thread.start();
            }
        });

    }
}
