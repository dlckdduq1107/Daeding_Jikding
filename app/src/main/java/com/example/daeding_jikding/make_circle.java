package com.example.daeding_jikding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;


public class make_circle extends AppCompatActivity {
    private int GALLERY_CODE = 10;//휴대폰 갤러리접근 코드
    private ImageView profile;
    private FirebaseStorage storage;//파이어베이스 storage에 접근
    private Button btn_create_circle;

    private EditText mEtCircle_name;
    private EditText mEtCircle_description;
    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    private ArrayList<String> members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_circle);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Daeding_Jikding");

        findViewById(R.id.profile).setOnClickListener(onClickListener);
        profile = (ImageView)findViewById(R.id.profile);
        mEtCircle_name = findViewById(R.id.et_cicrle_name);
        mEtCircle_description = findViewById(R.id.et_circle_description);
        storage = FirebaseStorage.getInstance();





    }


    View.OnClickListener onClickListener = new View.OnClickListener() {//이미지뷰를 클릭하면 loadAlbum 실행
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.profile:
                    loadAlbum();
                    break;
            }
        }
    };

    private void loadAlbum(){//갤러리 앱 실행
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);

    }


     protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {//갤러리에서 선택한 사진을 파이어 베이스 Storage에 업로드

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE){
            btn_create_circle = findViewById(R.id.btn_create_circle);
            //버튼 클릭시 동작
            btn_create_circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//동아리 생성 버튼 눌렀을때 데이터베이스에 사진 저장
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();//유저정보 가져옴
                    String token = firebaseUser.getUid() + String.valueOf((int)(Math.random()*10000));//동아리고유값 설정(유저정보 + 랜덤 숫자)

                    Uri file = data.getData();
                    StorageReference storageReference = storage.getReference();
                    StorageReference riversRef = storageReference.child("circle/"+token+".png");//저장될 경로

                    //버튼 눌렸을때 circle객체 데이터베이스에 저장
                    String strName = mEtCircle_name.getText().toString();
                    String strdes = mEtCircle_description.getText().toString();

                    Circle circleAccount = new Circle();//객체 생성
                    circleAccount.setCircleToken(token);
                    circleAccount.setCircle_name(strName);
                    circleAccount.setCircle_description(strdes);
                    String access_token = "https://firebasestorage.googleapis.com/v0/b/daeding-jikding.appspot.com/o/circle%2F" + token + ".png?alt=media";//토큰 위치
                    circleAccount.setProfile(access_token);
                    if (circleAccount.getMembers() != null){
                        members = circleAccount.getMembers();
                    }

                    members.add(firebaseUser.getUid());
                    circleAccount.setMembers(members);


                    UploadTask uploadTask = riversRef.putFile(file);//데이터베이스에 사진 업로드
                    mDatabaseRef.child("Circle").child(token).setValue(circleAccount);//데이터베이스에 객체 업로드

                    //사용자 클래스의 동아리 목록에 동아리 추가 구현 필요


                    finish();

                }
            });



            try {//비트맵 생성해서 이미지뷰에 출력
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);

                //비트맵 회전
                Matrix rotateMatrix = new Matrix();
                rotateMatrix.postRotate(90); //-360~360
                Bitmap update_img = Bitmap.createBitmap(img, 0, 0,
                        img.getWidth(), img.getHeight(), rotateMatrix, false);


                in.close();
                profile.setImageBitmap(update_img);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }





}