package com.example.ero.homework_load_image.providers;


import android.content.Context;

import com.example.ero.homework_load_image.R;
import com.example.ero.homework_load_image.models.Model;

import java.util.ArrayList;
import java.util.List;

public class UserProvider {
    static List<Model> list = new ArrayList<>();
    public static int position;

    public static List<Model> getUserList(Context context) {
        if (!list.isEmpty()) {
            list.clear();
        }
        for (int i = 0; i <context.getResources().getStringArray(R.array.url_image).length; i++) {
            list.add(new Model (context.getResources().getStringArray(R.array.name_image)[i],
                    context.getResources().getStringArray(R.array.url_image)[i], false));
        }
        return list;
    }
    public static Model getPosition() {
        return list.get(position);
    }
}
