package com.example.daeding_jikding;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


//동아리 리스트와 리사이클러뷰를 연결하는 어댑터
public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.CustomViewHolder> {

    private ArrayList<CircleAccount> arrayList;
    private Context context;


    public CustomAdaptor(ArrayList<CircleAccount> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdaptor.CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.profile);
        holder.circle_name.setText(arrayList.get(position).getCircle_name());
        holder.circle_description.setText(arrayList.get(position).getCircle_description());


    }

    @Override
    public int getItemCount() {

        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView circle_name;
        TextView circle_description;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profile = itemView.findViewById(R.id.profile);
            this.circle_name = itemView.findViewById(R.id.circle_name);
            this.circle_description = itemView.findViewById(R.id.circle_description);


        }
    }
}
