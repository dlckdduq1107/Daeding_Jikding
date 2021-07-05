package com.example.daeding_jikding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private EditText mEtEmail, mEtPwd; //로그인 입력 필드
    private CheckBox cb_autoLogin, cb_saveEmail; //자동 로그인, 이메일 저장 여부 체크박스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences("loginPref", MODE_PRIVATE);
        String prefEmail = pref.getString("strEmail", "");
        String prefPwd = pref.getString("strPwd", "");
        Boolean saveEmail = pref.getBoolean("saveEmail", false);
        Boolean autoLogin = pref.getBoolean("autoLogin", false);

        cb_autoLogin = (CheckBox)findViewById(R.id.cb_autoLogin);
        cb_saveEmail = (CheckBox)findViewById(R.id.cb_saveEmail);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Daeding_Jikding");


        mEtEmail = (EditText)findViewById(R.id.et_email);
        mEtPwd = (EditText)findViewById(R.id.et_pwd);


        //이메일 기억하기를 설정했던 경우
        if(saveEmail == true) {
            Log.e("saveEmail log", "prefEmail : "+ prefEmail);
            cb_saveEmail.setChecked(true);
            mEtEmail.setText(prefEmail);
        }
        else {
            mEtEmail.setText("");
        }

        //자동로그인을 설정했던 경우
        if(autoLogin == true) {
            cb_autoLogin.setChecked(true);
            mEtEmail.setText(prefEmail);
            mEtPwd.setText(prefPwd);

            //로그인 progress diaglog 창 생성
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("로그인 중...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            progressDialog.show();


            mFirebaseAuth.signInWithEmailAndPassword(prefEmail, prefPwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        // 로그인 성공

                        Intent intent = new Intent(Login.this, MainActivity.class);

                        progressDialog.dismiss();

                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }




        Button btn_login = findViewById(R.id.login_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인요청
                mEtEmail.setEnabled(false);
                mEtPwd.setEnabled(false);

                //로그인 progress diaglog 창 생성
                ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("로그인 중...");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                progressDialog.show();


                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // 로그인 성공

                            if(cb_autoLogin.isChecked() || cb_saveEmail.isChecked()) {
                                SharedPreferences pref = getSharedPreferences("loginPref", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("strEmail", strEmail);
                                editor.putString("strPwd", strPwd);

                                //자동로그인 체크 시 preference에 저장
                                if(cb_autoLogin.isChecked()) {
                                    editor.putBoolean("autoLogin", true);
                                }

                                //이메일 기억하기 체크 상태로 로그인 할 경우
                                if(cb_saveEmail.isChecked()) {
                                    editor.putBoolean("saveEmail", true);
                                }
                                editor.commit();
                            }

                            //자동로그인, 이메일 기억 모두 안하는 경우 저장된 이메일 삭제
                            else {
                                SharedPreferences pref = getSharedPreferences("loginPref", MODE_PRIVATE);
                                pref.edit().remove("saveEmail").commit();
                            }

                            Intent intent = new Intent(Login.this, MainActivity.class);

                            progressDialog.dismiss();

                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button btn_resister = findViewById(R.id.register_btn);
        //버튼 눌렀을때 행동
        btn_resister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 화면으로 이동
                Toast.makeText(Login.this, "화면 전환", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this, resister.class);
                startActivity(intent);
            }
        });

    }

}