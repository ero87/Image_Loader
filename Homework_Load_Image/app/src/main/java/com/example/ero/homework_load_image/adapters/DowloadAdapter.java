package com.example.ero.homework_load_image.adapters;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ero.homework_load_image.fragments.ImagDialogFragment;
import com.example.ero.homework_load_image.activitys.MainActivity;
import com.example.ero.homework_load_image.models.Model;
import com.example.ero.homework_load_image.R;
import com.example.ero.homework_load_image.providers.UserProvider;

import java.util.List;

public class DowloadAdapter extends RecyclerView.Adapter<DowloadAdapter.ViewHolder> {

    private static final String KEY = "kay";
    private Context context;
    private List<Model> list;
    private TextView textView;

    public DowloadAdapter(Context context, List<Model> list, TextView textView) {
        this.context = context;
        this.list = list;
        this.textView = textView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DowloadAdapter.ViewHolder holder, int position) {
        final Model model = list.get(position);
        holder.text.setText(model.getTitle());
        final FragmentManager fragment = ((MainActivity) context).getFragmentManager();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!model.isDownload()) {
                    textView.setText(model.getImageUrl());
                    UserProvider.position = holder.getAdapterPosition();
                } else {
                    textView.setText("Loading");
                    DialogFragment dialogFragment = new ImagDialogFragment();
                    dialogFragment.show(fragment, KEY);
                    UserProvider.position = holder.getAdapterPosition();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.title);
        }
    }
}
