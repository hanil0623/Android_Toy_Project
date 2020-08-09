package com.example.clone_project_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
/* DB 수정 관리하는 액티비티 */
/* 한 Row 값들의 Attribute 두 개가 모두 null이라면, Commit 버튼 시 해당 Row는 자동 삭제된다. */
/* 그렇지 않다면 해당 값들만 수정, 변경되는 작업 */
public class SettingActivity extends AppCompatActivity implements setAdapter.MyActionCallback {
    ArrayList<Info> setList = new ArrayList<Info>();
    ArrayList<TestClass> queryList = new ArrayList<TestClass>(); // Info 클래스 중 DB 쿼리 변경에 필요한 값들만 모아놓은 클래스
    DatabaseReference mDatabase2, mDatabase3;
    private ListView sListView;
    TextView ttView;

    public ArrayList<TestClass> getQueryList() {
        return queryList;
    }

    public void setQueryList(ArrayList<TestClass> queryList) {
        this.queryList = queryList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent g = getIntent();
        if(g.getExtras() != null && (g.getExtras().getInt("restartcall")==2)){
            Toast.makeText(getApplicationContext(), "Database Commit is Done !", Toast.LENGTH_SHORT).show();
        }
        final ImageButton queryButton = (ImageButton)findViewById(R.id.querybutton);
        Intent intent = getIntent();
        sListView = (ListView)findViewById(R.id.SettingView);
        ttView = (TextView)findViewById(R.id.TestSettingView);
        ImageView sTitleView = (ImageView)findViewById(R.id.SettingTitleView);

        sTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus() != null) {
                    getCurrentFocus().clearFocus();
                }
            }
        });

        // FAB 버튼 (commit button)
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getCurrentFocus() != null) {
                    getCurrentFocus().clearFocus();
                }
                Log.d("TAB", "Floating Action Button is Activated!");
                AlertDialog.Builder ad = new AlertDialog.Builder(SettingActivity.this);
                ad.setTitle("Security Check");
                ad.setMessage("확인시 DB 내용이 Commit되고, 액티비티 재시작합니다. 하시겠습니까? ");
                ad.setPositiveButton("넹넹", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 버튼 포커스 변경 시 남아있던 queryList를 가져옴
                        Log.d("TAG", "queryList 제대로 넘어오나 확인용 : " + queryList.toString());

                        for(TestClass T : queryList){
                            if(T.getMode() == 1){
                                // setValue의 수정 쿼리
                                mDatabase3 = FirebaseDatabase.getInstance().getReference();
                                Map<String, Object> tMap = new HashMap<String, Object>();

                                if(T.getStoreAddr() == null && T.getStoreName() != null) {
                                    tMap.put("업소명", T.getStoreName());
                                    mDatabase3.child(T.getKey()).updateChildren(tMap);
                                } else if(T.getStoreAddr() != null && T.getStoreName() == null){
                                    tMap.put("소재지(도로명)", T.getStoreAddr());
                                    mDatabase3.child(T.getKey()).updateChildren(tMap);
                                }
                            } else if (T.getMode() == 2){
                                // 삭제 쿼리인데 반드시 키가 있는지 살펴봐야겠지.
                                mDatabase3 = FirebaseDatabase.getInstance().getReference();

                                String imsi = mDatabase3.child(T.getKey()).toString();
                                Log.d("TAG", "해당하는 Object 삭제하기 전 확인함 : " + imsi);
                                // 삭제가 정상적으로 된다.
                                mDatabase3.child(T.getKey()).removeValue();
                            }
                        }
                        //Toast.makeText(getApplicationContext(), "Database Commit is Done !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                        i.putExtra("restartcall", 2);
                        startActivity(i);
                    }
                });
                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });
        // 각 이미지 버튼(항목별)을 클릭 시 쿼리를 통해 값들을 보여주는 작업
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
                        } else if(item.getTitle().toString().equals("커피숍")){
                            queryButton.setImageResource(R.drawable.coffeequeryimage);
                        } else if(item.getTitle().toString().equals("다방")){
                            queryButton.setImageResource(R.drawable.cafequeryimage);
                        } else if(item.getTitle().toString().equals("업소명(가나다)")){
                            queryButton.setImageResource(R.drawable.namequeryimage3);
                        } else if(item.getTitle().toString().equals("패스트푸드")){
                            queryButton.setImageResource(R.drawable.fastfoodqueryimage);
                        } else if(item.getTitle().toString().equals("편의점")){
                            queryButton.setImageResource(R.drawable.conveniencequeryimage);
                        } else if(item.getTitle().toString().equals("기타")){
                            queryButton.setImageResource(R.drawable.etcqueryimage);
                        } else if(item.getTitle().toString().equals("아이스크림")){
                            queryButton.setImageResource(R.drawable.icecreamqueryimage);
                        }
                        getQuery(query);
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
        Query q;
        if(query.equals("기타")) query = "기타 휴게음식점";
        if(query.equals("업소명(가나다)")) { q = mDatabase2.orderByChild("업소명");}
        else {q = mDatabase2.orderByChild("업태명").equalTo(query);}

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
                                        if(temp2.length > 1) {
                                            tempInfo.setStoreName(temp2[1]);
                                        }
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
        setAdapter sAdapter = new setAdapter(this);
        for (Info d : setList){
            sAdapter.addItem(d.getKey(), d.getStoreAddr(), d.getStoreName());
            //sAdapter.addItem(d.getStoreAddr(), d.getStoreAddr2(), d.getStoreName(), d.getStoreHash(), d.getStoreTell(), d.getStoreType());
        }
        /* 리스트뷰에 어댑터 등록 */
        sListView.setAdapter(sAdapter);
    }

    @Override
    public void onActionPerformed(ArrayList<TestClass> T) {
        queryList = T;
    }
}
