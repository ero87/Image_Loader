package com.example.ero.homework_load_image.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ero.homework_load_image.R;
import com.example.ero.homework_load_image.adapters.DowloadAdapter;
import com.example.ero.homework_load_image.providers.UserProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1001;
    private Button button;
    private TextView text;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editText_id);
        button = findViewById(R.id.button_id);
         progressBar = findViewById(R.id.progressbar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final DowloadAdapter adapter = new DowloadAdapter(this, UserProvider.getUserList(this), text);
        recyclerView.setAdapter(adapter);

        requestPermissions();
    }

    private void loadMethod(final TextView text, Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            Bitmap bitmap;
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            loadImage();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, R.string.image_load, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }

            private void loadImage() throws IOException {
                InputStream inputStream;
                URL url = new URL(text.getText().toString());
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.connect();
                inputStream = httpConn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File file = new File(path + "/" + UserProvider.getPosition().getTitle() + ".jpg");
                UserProvider.getPosition().setDownload(true);
                FileOutputStream fileOutput = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutput);
                byte[] buffer = new byte[1024];
                int bufferLength;
                while (-1 != (bufferLength = inputStream.read(buffer.clone()))) {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();
                inputStream.close();
            }
        });
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadMethod(text, button);
                } else {
                    requestPermissions();
                }
        }
    }
}
