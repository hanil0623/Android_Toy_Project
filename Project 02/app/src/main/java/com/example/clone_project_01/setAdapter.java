package com.example.clone_project_01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class setAdapter extends BaseAdapter {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.settingview_custom, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        //ImageButton iv_img1 = (ImageButton) convertView.findViewById(R.id.SearchNameButton);
        //ImageButton iv_img2 = (ImageButton) convertView.findViewById(R.id.SearchAddrButton);
        final EditText c1 = (EditText) convertView.findViewById(R.id.column1);
        final EditText c2 = (EditText) convertView.findViewById(R.id.column2);
        /*
        final EditText c3 = (EditText) convertView.findViewById(R.id.column3);
        final EditText c4 = (EditText) convertView.findViewById(R.id.column4);
        final EditText c5 = (EditText) convertView.findViewById(R.id.column5);
        final EditText c6 = (EditText) convertView.findViewById(R.id.column6);*/

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final Info myItem = getItem(position);


        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        //iv_img1.setImageDrawable(myItem.getItem1());
        //iv_img2.setImageDrawable(myItem.getItem2());
        c1.setText(myItem.getStoreName());
        c2.setText(myItem.getStoreAddr());
        /*
        c3.setText(myItem.getStoreAddr2());
        c4.setText(myItem.getStoreName());
        c5.setText(myItem.getStoreHash());
        c6.setText(myItem.getStoreTell());*/

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        /*
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
                Toast.makeText(context, "선택: "+myItem.getStoreAddr(), Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });*/

        return convertView;
    }
    //d.getStoreAddr(), d.getStoreAddr2(), d.getStoreName(), d.getStoreHash(), d.getStoreTell(), d.getStoreType()
    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String addr, String name) {
    //public void addItem(String addr, String addr2, String name, String hash, String tell, String type) {

        Info mItem = new Info();

        /* MyItem에 아이템을 setting한다. */
        // mItem.setItem1(Item1);
        // mItem.setItem2(Item2);
        mItem.setStoreName(name);
        mItem.setStoreAddr(addr);
        //mItem.setStoreAddr2(addr2);
        //mItem.setStoreHash(hash);
        //mItem.setStoreTell(tell);
        //mItem.setStoreType(type);
//mItem.set
        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}
