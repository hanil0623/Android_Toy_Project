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

public class MainActivity extends AppCompatActivity {
    ImageView mainview;
    ImageButton iButton;
    ArrayList<Info> alist = new ArrayList<Info>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainview = (ImageView)findViewById(R.id.bg_main);
        iButton = (ImageButton)findViewById(R.id.btn_click);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //final DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(postListener);

        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(mainview);
        Glide.with(this).load(R.drawable.bgcolor65).centerCrop().placeholder(R.drawable.bgcolor65).into(gifImage);


        iButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
                i.putParcelableArrayListExtra("alist", alist);
                startActivity(i);
            }
        });
    }

    ValueEventListener postListener = new ValueEventListener(){
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                String s = snapshot.getValue().toString();
                s = s.substring(1, s.length()-1);
                Info tempInfo = new Info();
                int cnt = 0;
                String[] temp2 = {};
                StringTokenizer token = new StringTokenizer(s, ",");
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
                        temp2 = temp.split("=");
                        if(temp2[0].contains("업소명")){
                            tempInfo.setStoreName(temp2[1]);
                        } else if(temp2[0].contains("업종명")){
                            tempInfo.setStoreType(temp2[1]);
                        } else if(temp2[0].contains("업태명")){
                            tempInfo.setStoreHash(temp2[1]);
                        } else if(temp2[0].contains("연락처")){
                            if(temp2.length > 1){
                                tempInfo.setStoreTell(temp2[1]);
                            }
                        } else if(temp2[0].contains("데이터기준일자")){
                            tempInfo.setStoreDate(temp2[1]);
                        }
                    }
                    else{
                        temp2[1] = temp2[1] + ',' + temp;
                    }
                }
                alist.add(tempInfo);
            }
            //System.out.println(alist.size());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w("Database", "Failed to read value", databaseError.toException());
        }
    };
}
