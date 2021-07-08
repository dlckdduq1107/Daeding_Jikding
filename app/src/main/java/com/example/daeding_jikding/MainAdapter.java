package com.example.daeding_jikding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
    private ArrayList<Circle> circles;

    public MainAdapter(ArrayList<Circle> circles){
        this.circles = circles;
    }

    public static class MainHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_desc, tv_num;

        public MainHolder(View view){
            super(view);
            this.tv_name = view.findViewById(R.id.circle_name);
            this.tv_desc = view.findViewById(R.id.circle_desc);
            this.tv_num = view.findViewById(R.id.member_num);
        }
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_holder_view, parent, false);
        MainHolder mainHolder = new MainHolder(holderView);

        return mainHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int position) {
        mainHolder.tv_name.setText(circles.get(position).getCircle_name());
        mainHolder.tv_desc.setText(circles.get(position).getCircle_description());
        mainHolder.tv_num.setText(Integer.toString(circles.get(position).getMembers().size()));

        mainHolder.itemView.setTag(position);
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String circle_name = mainHolder.tv_name.getText().toString();
                Toast.makeText(v.getContext(), circle_name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return circles != null ? circles.size() : 0;
    }
}


