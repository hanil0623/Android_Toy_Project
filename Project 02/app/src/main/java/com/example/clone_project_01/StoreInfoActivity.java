package com.example.clone_project_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
/* 주소 정보를 받아 위도, 경도 값으로 바꿔주기 위한 액티비티 */
public class StoreInfoActivity extends AppCompatActivity {
    double lat = -1, lang = -1;
    String storename, storeaddr, storetype, storehash, storetell, storeaddr2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);

        Intent intent = getIntent();
        TextView storeNameView = (TextView) findViewById(R.id.storeNameView);
        TextView storeTypeView = (TextView) findViewById(R.id.storeTypeView);
        TextView storeAddrView = (TextView) findViewById(R.id.storeAddrView);
        TextView storeTellView = (TextView) findViewById(R.id.storeTellView);

        storename = intent.getStringExtra("storename");
        storeaddr = intent.getStringExtra("storeaddr");
        storetype = intent.getStringExtra("storetype");
        storehash = intent.getStringExtra("storehash");
        storetell = intent.getStringExtra("storetell");
        storeaddr2 = intent.getStringExtra("storeaddr2");

        storeNameView.setText(storename);
        storeTypeView.setText(storetype);
        storeTellView.setText(storetell);
        storeAddrView.setText(storeaddr);

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocationName(storeaddr, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "서버-주소 변환 에러");
        }

        if (list != null) {
            if (list.size() == 0) {
                storeAddrView.setText("주소 정보 미출력");
            } else {
                lat = list.get(0).getLatitude();
                lang = list.get(0).getLongitude();
                //t3.setText("해당 주소의 위도는 : " + list.get(0).getLatitude());
                //t4.setText("해당 주소의 경도는 : " + list.get(0).getLongitude());
            }
        }
        if (savedInstanceState == null) {
            // main에서 fragment로 어케 데이터 전달했더라.
            MapFragment mFragment = new MapFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, mFragment, "main").commit();

            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", lat);
            bundle.putDouble("longitude", lang);
            bundle.putString("storename", storename);
            mFragment.setArguments(bundle);
        }
    }
}
