package com.example.daeding_jikding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {
    private View view;
    private Button btn_make_circle;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CircleAccount> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_user;
    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private List<String> userList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.circle_list);//아이디 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//CircleAccount객체 담을 어레이 리스트( 어댑터 쪽으로)
        userList = new ArrayList<>();//user정보 담을 어레이 리스트

        database = FirebaseDatabase.getInstance();// 파이어베이스 데이터베이스 연동
        mFirebaseAuth = FirebaseAuth.getInstance();




        //유저가 속한 동아리 목록을 추가해주는 부분
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();//유저정보 가져옴
        String token = firebaseUser.getUid();
        databaseReference_user = database.getReference("Daeding_Jikding/UserAccount/"+token.toString()+"/circle_list");//DB User 테이블/동아리 목록 연동
        databaseReference_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){// 반복문으로 데이터 리스트를 추출해냄
                    String name = (String) snapshot.getValue();//circle_list의 요소를 하나씩 name으로 저장
                    userList.add(name);//리스트에 저장
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        databaseReference = database.getReference("Daeding_Jikding/CircleAccount");//DB Circle 테이블 연동

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();//기존 배열 리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){// 반복문으로 데이터 리스트를 추출해냄
                    CircleAccount circle = snapshot.getValue(CircleAccount.class);//만들어놨던 CircleAccount객체에 데이터를 담는다.
                    if(userList.contains(circle.getCircle_name())){//동아리가 유저에 가입한 동아리에 있으면
                        arrayList.add(circle);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                    }

                }
                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던 중 에러 발생시
                Log.e("home", String.valueOf(databaseError.toException()));

            }
        });

        adapter = new CustomAdaptor(arrayList, container.getContext());
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결











        btn_make_circle = view.findViewById(R.id.make_circle);
        //버튼 클릭시 동작
        btn_make_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;

    }





}