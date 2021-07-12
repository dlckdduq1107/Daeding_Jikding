package com.example.daeding_jikding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
    private FirebaseDatabase database;
    private DatabaseReference mDatabaseRef;
    private ArrayList<Circle> circles; // 동아리 목록을 저장할 필드
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private androidx.appcompat.widget.SearchView search_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_circle, container, false);

        circles = new ArrayList<>();

        // recycler view 적용
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MainAdapter(circles);
        recyclerView.setAdapter(mainAdapter);


        //검색 시 동아리 목록에 동아리 추가
        search_view = (androidx.appcompat.widget.SearchView)view.findViewById(R.id.search_view);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //검색 버튼을 눌렀을 때
            @Override
            public boolean onQueryTextSubmit(String query) {
                //동아리 목록 초기화
                circles.clear();

                //동아리 검색
                database = FirebaseDatabase.getInstance();
                mDatabaseRef = database.getReference("Daeding_Jikding/Circle");
                mDatabaseRef.orderByChild("circle_name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Circle c = ds.getValue(Circle.class);
                            Log.d("cname", c.getCircle_name());
                            //검색어가 동아리 이름에 포함된 경우만 목록에 추가
                            if(c.getCircle_name().contains(query)) {
                                circles.add(c);
                            }
                        }

                        //adapter 재적용
                        mainAdapter = new MainAdapter(circles);
                        recyclerView.setAdapter(mainAdapter);
                        mainAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }


                });
                search_view.clearFocus(); // onQueryTextSubmit 이벤트 중복발생 방지
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

}