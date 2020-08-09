package com.example.clone_project_01;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Fragment2 extends Fragment {
    EditText searchText;
    private ListView mListView;
    boolean isSearch;
    ArrayList<Info> data, addList;
    Info now;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        data = bundle.getParcelableArrayList("gdata");
        isSearch = false;
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchText = view.findViewById(R.id.searchText);
        final View vview = view;

        mListView = vview.findViewById(R.id.listView);
        ImageView iView = (ImageView)vview.findViewById(R.id.imageView3);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), StoreInfoActivity.class);

                intent.putExtra("storename", addList.get(position).getStoreName());
                intent.putExtra("storeaddr", addList.get(position).getStoreAddr());
                intent.putExtra("storeaddr2", addList.get(position).getStoreAddr2());
                intent.putExtra("storetype", addList.get(position).getStoreType());
                intent.putExtra("storetell", addList.get(position).getStoreTell());
                intent.putExtra("storehash", addList.get(position).getStoreHash());
                startActivity(intent);
            }
        });

        iView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout lo = (LinearLayout)vview.findViewById(R.id.tastelayout);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)lo.getLayoutParams();

                lp.topMargin = 10;
                lo.setLayoutParams(lp);
                searchText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
            }
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((actionId == EditorInfo.IME_ACTION_DONE) || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_NEXT)){

                    LinearLayout lo = (LinearLayout)vview.findViewById(R.id.chickenlayout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT, 350);
                    lo.setLayoutParams(lp);

                    String sName = searchText.getText().toString();
                    // alist를 돌면서 sName이 있는 list값 반환하면 된다.
                    mListView = (ListView)vview.findViewById(R.id.listView);

                    // tasteView
                    ImageView tView = vview.findViewById(R.id.tasteView);
                    LinearLayout.LayoutParams tving = (LinearLayout.LayoutParams)tView.getLayoutParams();
                    if(isSearch == false) {
                        tving.weight = (float) (tving.weight * 0.5);
                        tving.height = (int) (tving.height * 0.5);
                        isSearch = true;
                    }
                    tView.setLayoutParams(tving);

                    LinearLayout lo2 = (LinearLayout)vview.findViewById(R.id.tastelayout);
                    FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams)lo2.getLayoutParams();

                    lp2.topMargin = 10;
                    lo2.setLayoutParams(lp2);

                    searching_data(sName);
                    dataSetting();
                }
                return false;
            }
        });

        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LinearLayout lo = (LinearLayout)vview.findViewById(R.id.tastelayout);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)lo.getLayoutParams();
                lp.topMargin = 10;
                lo.setLayoutParams(lp);
                return false;
            }
        });
    }

    public void searching_data(String pattern){
        addList = new ArrayList<Info>();
        for(Info T : data){
            if(T.getStoreName().contains(pattern)){
                addList.add(T);
            }
        }
    }

    private void dataSetting(){

        MyAdapter mMyAdapter = new MyAdapter();

        for (Info d : addList){
            mMyAdapter.addItem(d.getStoreAddr(), d.getStoreAddr2(), d.getStoreName(), d.getStoreHash(), d.getStoreTell(), d.getStoreType());
        }

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }
}
