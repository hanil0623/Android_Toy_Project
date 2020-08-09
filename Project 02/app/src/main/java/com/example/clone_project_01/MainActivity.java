package com.example.clone_project_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;
/* Firebase에 등록된 OpenAPI json 파일을 쿼리를 통해 가져와 alist에 넣기 위한 메인 액티비티 */
public class MainActivity extends AppCompatActivity {
    ImageView mainview;
    ImageButton iButton;
    ArrayList<Info> alist = new ArrayList<Info>(); // json 파일을 담기 위한 arraylist
    DatabaseReference mDatabase; // DB 레퍼런스 객체, 이를 통해 쿼리 작업 수행

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainview = (ImageView)findViewById(R.id.bg_main);
        iButton = (ImageButton)findViewById(R.id.btn_click);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(postListener);

        // 움직이는 화면(GIF) 파일 업로드하기 위한 Glide 라이브러리
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(mainview);
        Glide.with(this).load(R.drawable.bgcolor65).centerCrop().placeholder(R.drawable.bgcolor65).into(gifImage);

        // click 버튼을 누르면 Parcelable 객체를 통해 intent에 넣는 작업
        iButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
                i.putParcelableArrayListExtra("alist", alist);
                startActivity(i);
            }
        });
    }

    // Firebase 쿼리를 가져와 파싱하는 메소드
    ValueEventListener postListener = new ValueEventListener(){
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                // s에는 {"가게명":"ABCDE", "업종명":"휴게음식점", "연락처":"010-xxxx-xxxx",...}와 같이 주어진다.
                // 이를 가게명, ABCDE, 업종명, 휴게음식점 등과 같이 파싱하여 Info 객체의 멤버변수에 넣는 로직
                String s = snapshot.getValue().toString();
                s = s.substring(1, s.length()-1); // 양 끝의 괄호를 없애줌
                Info tempInfo = new Info();
                int cnt = 0;
                String[] temp2 = {};
                StringTokenizer token = new StringTokenizer(s, ","); // 콤마를 기준으로 split 작업
                while(token.hasMoreTokens()){
                    String temp = token.nextToken();

                    if(temp.contains("=")){
                        temp = temp.trim();
                        if(temp2.length > 1) {
                            if (temp2[0].contains("소재지(도로명)") && tempInfo.getStoreAddr() == null) {
                                if (temp2.length > 1) {
                                    tempInfo.setStoreAddr(temp2[1]);
                                }
                            } else if (temp2[0].contains("소재지(지번)") && tempInfo.getStoreAddr2() == null) {
                                if (temp2.length > 1) {
                                    tempInfo.setStoreAddr2(temp2[1]);
                                }
                            }
                        }
                        // 혹시 모를 오류 대비 try catch
                        try {
                            temp2 = temp.split("=");
                            if (temp2[0].contains("업소명")) {
                                if (temp2.length > 1) {
                                    tempInfo.setStoreName(temp2[1]);
                                }
                            } else if (temp2[0].contains("업종명")) {
                                tempInfo.setStoreType(temp2[1]);
                            } else if (temp2[0].contains("업태명")) {
                                tempInfo.setStoreHash(temp2[1]);
                            } else if (temp2[0].contains("연락처")) {
                                if (temp2.length > 1) {
                                    tempInfo.setStoreTell(temp2[1]);
                                }
                            } else if (temp2[0].contains("데이터기준일자")) {
                                tempInfo.setStoreDate(temp2[1]);
                            }
                        } catch(IndexOutOfBoundsException e){
                            Log.d("TAG", "여기 인덱스 벗어남" + e);
                        }
                    }
                    else{
                        temp2[1] = temp2[1] + ',' + temp;
                    }
                }
                alist.add(tempInfo);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("Database", "Failed to read value", databaseError.toException());
        }
    };
}
