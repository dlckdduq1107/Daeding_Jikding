package com.example.daeding_jikding;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

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
        mainHolder.tv_num.setText("인원 : " + Integer.toString(circles.get(position).getMembers().size()));

        mainHolder.itemView.setTag(position);
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 동아리 목록에서 클릭할 경우
                String circle_name = mainHolder.tv_name.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference("Daeding_Jikding/Circle");
                mDatabaseRef.orderByChild("circle_name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Circle c = ds.getValue(Circle.class);

                            //선택한 동아리를 firebase db에서 찾아 intent로 전달함
                            if(c.getCircle_name().compareTo(circle_name) == 0) {
                                Intent intent = new Intent(v.getContext(), CircleActivity.class);
                                intent.putExtra("circle", c);
                                v.getContext().startActivity(intent);

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return circles != null ? circles.size() : 0;
    }
}


