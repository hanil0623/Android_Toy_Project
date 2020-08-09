package com.example.clone_project_01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class setAdapter extends BaseAdapter {
    private ArrayList<Info> mItems = new ArrayList<>();
    public ArrayList<TestClass> queryList = new ArrayList<TestClass>();
    Info T, T2;
    SettingActivity SA = new SettingActivity();

    public interface MyActionCallback{
        void onActionPerformed(ArrayList<TestClass> T);
    }

    public setAdapter(MyActionCallback actionCallback) {
        mActionCallback = actionCallback;
    }

    private MyActionCallback mActionCallback;

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

    public ArrayList<TestClass> getQueryList(){ return queryList; }
    public void setQueryList(ArrayList<TestClass> T) { queryList = T; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        final View tView = convertView;
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


        c1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    T = new Info();
                    //c1.setSelection(c1.getText().length()-1);
                    String text = c1.getText().toString();
                    //isfocus = true;
                    if (T.getStoreName() == null) {
                        T.setKey(myItem.getKey());
                        T.setStoreName(text);
                    } else {
                        Log.d("TAG", "있으면 안될 오류임");
                    }
                }
                return false;
            }
        });

        c1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String compare = c1.getText().toString();
                    try {
                        TestClass TC = new TestClass();
                        if (compare.equals("")) {
                            if (c2.getText().toString().equals("")) {
                                TC.setKey(T.getKey());
                                TC.setMode(2);
                                queryList.add(TC);
                                mActionCallback.onActionPerformed(queryList);
                            } else {
                                TC.setKey(T.getKey());
                                TC.setMode(1);
                                TC.setStoreName("");
                                queryList.add(TC);
                                mActionCallback.onActionPerformed(queryList);
                            }
                        } else if (!T.getStoreName().equals(compare)) {
                            TC.setKey(T.getKey());
                            TC.setStoreName(compare);
                            TC.setMode(1);
                            queryList.add(TC);
                            SA.setQueryList(queryList);
                            mActionCallback.onActionPerformed(queryList);
                        }
                        Log.d("TAG", "c1의 포커스 없 없 없 다.");
                        //c1.setText(compare);
                        //notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.d("TAG", "getStoreName() Object Error!" + e);
                    }
                } else {
                    //c1.setFocusable(true);
                    Log.d("TAG", "c1의 포커스 있 있 있 다.");
                }
            }
        });

        c2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    T2 = new Info();
                    c2.setSelection(c2.getText().length() - 1);
                    String text = c2.getText().toString();
                    if (T2.getStoreAddr() == null) {
                        T2.setKey(myItem.getKey());
                        T2.setStoreAddr(text);
                    } else {
                        Log.d("TAG", "있으면 안될 오류임");
                    }
                    //Log.d("TAG", "where is focus? " + convertView.setFocusable(true))
                }
                return false;
            }
        });

        c2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Log.d("TAG", "여기는 c2에 포커스 없을때 일어난다.");
                    String compare = c2.getText().toString();
                    try {
                        TestClass TC = new TestClass();
                        if (compare.equals("")) {
                            if (c1.getText().toString().equals("")) {
                                // 삭제작업 가게명이 둘다 없다면 삭제
                                TC.setKey(T2.getKey());
                                TC.setMode(2);
                                queryList.add(TC);
                                mActionCallback.onActionPerformed(queryList);
                            } else {
                                // 하나라도 있다면 그냥 null값으로 변경 쿼리
                                TC.setKey(T2.getKey());
                                TC.setStoreAddr("");
                                TC.setMode(1);
                                queryList.add(TC);
                                mActionCallback.onActionPerformed(queryList);
                            }
                        } else if (!T2.getStoreAddr().equals(compare)) {
                            TC.setKey(T2.getKey());
                            TC.setMode(1);
                            TC.setStoreAddr(compare);
                            queryList.add(TC);
                            mActionCallback.onActionPerformed(queryList);
                        }
                        //notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.d("TAG", "T2.getStoreAddr Error" + e);
                    }
                }
                else{
                    Log.d("TAG", "여기는 c2에 포커스 있을때 일어난다.");
                }
            }
        });

        return convertView;
    }

    //d.getStoreAddr(), d.getStoreAddr2(), d.getStoreName(), d.getStoreHash(), d.getStoreTell(), d.getStoreType()
    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String key, String addr, String name) {
    //public void addItem(String addr, String addr2, String name, String hash, String tell, String type) {

        Info mItem = new Info();

        /* MyItem에 아이템을 setting한다. */
        // mItem.setItem1(Item1);
        // mItem.setItem2(Item2);
        mItem.setKey(key);
        mItem.setStoreName(name);
        mItem.setStoreAddr(addr);
        //mItem.setStoreAddr2(addr2);
        //mItem.setStoreHash(hash);
        //mItem.setStoreTell(tell);
        //mItem.setStoreType(type);
        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}
