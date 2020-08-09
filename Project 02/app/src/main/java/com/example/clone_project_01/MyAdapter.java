package com.example.clone_project_01;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

/* DB 검색 결과를 동적 ListView로 보여주기 위한 MyAdapter Class */
public class MyAdapter extends BaseAdapter {
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<Info> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Info getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    // 동적 ListView로 보여주기 위해 꼭 필요한 getView 메소드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        final TextView tv_name = (TextView) convertView.findViewById(R.id.SearchStoreName) ;
        final TextView tv_contents = (TextView) convertView.findViewById(R.id.SearchStoreAddr) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final Info myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        tv_name.setText(myItem.getStoreName());
        tv_contents.setText(myItem.getStoreAddr());

       // 해당 위젯을 클릭 시 리스너를 작성하여 StoreInfoActivity(주소정보-> 위도, 경도값 변환 액티비티)로 옮겨주기 위한 작업
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StoreInfoActivity.class);
                intent.putExtra("storename", myItem.getStoreName());
                intent.putExtra("storeaddr", myItem.getStoreAddr());
                intent.putExtra("storeaddr2", myItem.getStoreAddr2());
                intent.putExtra("storetype", myItem.getStoreType());
                intent.putExtra("storetell", myItem.getStoreTell());
                intent.putExtra("storehash", myItem.getStoreHash());
                //Toast.makeText(context, "선택: "+myItem.getStoreName(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
        tv_contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "선택: "+tv_contents.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), StoreInfoActivity.class);

                intent.putExtra("storename", myItem.getStoreName());
                intent.putExtra("storeaddr", myItem.getStoreAddr());
                intent.putExtra("storeaddr2", myItem.getStoreAddr2());
                intent.putExtra("storetype", myItem.getStoreType());
                intent.putExtra("storetell", myItem.getStoreTell());
                intent.putExtra("storehash", myItem.getStoreHash());
                //Toast.makeText(context, "선택: "+myItem.getStoreAddr(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public void addItem(String addr, String addr2, String name, String hash, String tell, String type) {
        Info mItem = new Info();
        mItem.setStoreName(name);
        mItem.setStoreAddr(addr);
        mItem.setStoreAddr2(addr2);
        mItem.setStoreName(name);
        mItem.setStoreHash(hash);
        mItem.setStoreTell(tell);
        mItem.setStoreType(type);
        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);
    }
}
