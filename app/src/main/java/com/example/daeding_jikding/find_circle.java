package com.example.daeding_jikding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class find_circle extends Fragment {
    private DatabaseReference mDatabaseRef;
    private ArrayList<Circle> circles = new ArrayList<>(); // 동아리 목록을 저장할 필드
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchView search_view;

    //미구현
    public void searchCircles(String query) {
        ArrayList<Circle> circles = new ArrayList<>();
        mDatabaseRef.child("CircleAccount").orderByChild("circle_name").equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Circle c = snapshot.getChildren().iterator().next().getValue(Circle.class);
                circles.add(c);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_circle, container, false);

        //검색 시 동아리 목록에 동아리 추가
        search_view = view.findViewById(R.id.search_view);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCircles(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // recycler view 적용
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter = new MainAdapter(circles);
        recyclerView.setAdapter(mainAdapter);


        return view;
    }
}