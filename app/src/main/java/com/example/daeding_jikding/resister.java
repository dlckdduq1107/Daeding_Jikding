package com.example.daeding_jikding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.InstrumentationInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class resister extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtEmail, mEtPwd; //회원가입 입력 필드
    private Button mBtnResister;// 회원가입 버튼

    private List<String> circles = new ArrayList<>();//임시로 동아리 목록 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Daeding_Jikding");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnResister = findViewById(R.id.btn_resister);

        mBtnResister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                Log.d("email", strEmail);
                Log.d("pwd", strPwd);

                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(resister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        //task로 로그인여부가 나온다.
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();//유저정보 가져옴
                            UserAccount account = new UserAccount();//객체 생성
                            account.setIdToken(firebaseUser.getUid());//고유값 설정
                            account.setEmailId(firebaseUser.getEmail());//파이어베이스로 부터 아이디 가져옴
                            account.setPassword(strPwd);

                            //동아리 임시데이터 삽입
                            circles.add("AkRXtAGsb6ew3AB1clu6XLCzYLu11170");
                            circles.add("AkRXtAGsb6ew3AB1clu6XLCzYLu12974");
                            account.setCircle_list(circles);




                            //setValue : 데이터베이스에 insert 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(resister.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(resister.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}