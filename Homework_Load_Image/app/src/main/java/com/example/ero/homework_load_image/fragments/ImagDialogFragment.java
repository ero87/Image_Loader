package com.example.ero.homework_load_image.fragments;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ero.homework_load_image.R;
import com.example.ero.homework_load_image.providers.UserProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImagDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        final ImageView image = view.findViewById(R.id.fr_image);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(path + "/" + UserProvider.getPosition().getTitle() + ".jpg");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    final Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    fileInputStream.close();
                    image.post(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return view;
    }
}
