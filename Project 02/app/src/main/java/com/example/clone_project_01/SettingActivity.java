package com.example.clone_project_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SettingActivity extends AppCompatActivity {
    ArrayList<Info> setList = new ArrayList<Info>();
    DatabaseReference mDatabase2;
    private ListView sListView;
    TextView ttView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final ImageButton queryButton = (ImageButton)findViewById(R.id.querybutton);
        Intent intent = getIntent();
        //setting_data = intent.getParcelableArrayListExtra("DB");
        sListView = (ListView)findViewById(R.id.SettingView);
        ttView = (TextView)findViewById(R.id.TestSettingView);
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        queryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu pm = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.popup_menu, pm.getMenu());

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String query = item.getTitle().toString();
                        if(item.getTitle().toString().equals("과자점")){
                            queryButton.setImageResource(R.drawable.snackqueryimage);
                            //showUserName(query);
                            getQuery(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("커피숍")){
                            queryButton.setImageResource(R.drawable.coffeequeryimage);
                            //showUserName(query);
                            getQuery(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("다방")){
                            queryButton.setImageResource(R.drawable.cafequeryimage);
                            getQuery(query);
                            //showUserName(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("업소명(가나다)")){
                            queryButton.setImageResource(R.drawable.namequeryimage3);
                            //showUserName(query);
                            getQuery(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("패스트푸드")){
                            queryButton.setImageResource(R.drawable.fastfoodqueryimage);
                            //showUserName(query);
                            getQuery(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("편의점")){
                            queryButton.setImageResource(R.drawable.conveniencequeryimage);
                            //showUserName(query);
                            getQuery(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("기타")){
                            queryButton.setImageResource(R.drawable.etcqueryimage);
                            getQuery(query);
                            //dataSetting();
                        } else if(item.getTitle().toString().equals("아이스크림")){
                            queryButton.setImageResource(R.drawable.icecreamqueryimage);
                            getQuery(query);
                            //dataSetting();
                        }
                        return false;
                    }
                });
                pm.show();
            }
        });
    }

    public void getQuery(String query){
        setList.clear();
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        Query q = mDatabase2.orderByChild("업태명").equalTo(query);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int j = 0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        try {
                            String key = snapshot.getKey();
                            String s = snapshot.getValue().toString();
                            // s값 받아서 파싱해서
                            //String st = snapshot.getValue(String.class);
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
                            tempInfo.setKey(key);
                            setList.add(tempInfo);
                            //System.out.println("vals is : " + vals);
                        } catch(ClassCastException e){
                            e.printStackTrace();
                        }
                        j++;
                    }
                    // 뭐야 콜백이라 늦게 실행되네
                    dataSetting();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Test", "No No");
            }
        });
    }

    private void dataSetting(){
        setAdapter sAdapter = new setAdapter();
        for (Info d : setList){
            sAdapter.addItem(d.getStoreAddr(), d.getStoreName());
            //sAdapter.addItem(d.getStoreAddr(), d.getStoreAddr2(), d.getStoreName(), d.getStoreHash(), d.getStoreTell(), d.getStoreType());
        }

        /* 리스트뷰에 어댑터 등록 */
        sListView.setAdapter(sAdapter);
    }
}
