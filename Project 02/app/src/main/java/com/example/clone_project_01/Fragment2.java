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

/* 검색 화면 제어를 위한 프래그먼트 */
public class Fragment2 extends Fragment {
    EditText searchText; // 검색 위젯 변수
    private ListView mListView; // 검색 결과를 출력할 어댑터 위젯 변수
    boolean isSearch; // 검색 시에 이미지 뷰 값 유지를 위한 변수
    ArrayList<Info> data, addList; // data : DB 파싱 결과를 담아놓은 data, addList : 검색 결과에 해당하는 값만 모아놓은 배열

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // MainActivity에서 파싱한 결과에 해당하는 ArrayList 값을 얻어옴. (Activity->Fragment)
        Bundle bundle = getArguments();
        data = bundle.getParcelableArrayList("gdata");
        isSearch = false;
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchText = view.findViewById(R.id.searchText);
        final View vview = view; // inner class에 접근하기 위한 뷰 변수 선언
        mListView = vview.findViewById(R.id.listView);
        ImageView iView = (ImageView)vview.findViewById(R.id.imageView3);

        // 어댑터 뷰에 해당하는 item을 클릭할 때 저장 정보를 보여주기 위한 리스너
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), StoreInfoActivity.class);

                // Fragment2 -> StoreInfoActivity
                // DB 파싱 결괏값을 가진 addList의 Info 멤버 변수들을 인텐트 객체에 담는다.
                intent.putExtra("storename", addList.get(position).getStoreName());
                intent.putExtra("storeaddr", addList.get(position).getStoreAddr());
                intent.putExtra("storeaddr2", addList.get(position).getStoreAddr2());
                intent.putExtra("storetype", addList.get(position).getStoreType());
                intent.putExtra("storetell", addList.get(position).getStoreTell());
                intent.putExtra("storehash", addList.get(position).getStoreHash());
                startActivity(intent);
            }
        });

        // 검색창에서 배경화면 클릭할 때 발생하는 리스너
        iView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // <동구의맛> 이미지뷰 값을 얻어옴
                LinearLayout lo = (LinearLayout)vview.findViewById(R.id.tastelayout);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)lo.getLayoutParams();
                // 버튼 클릭 시 위젯 속성들을 코드로 제어하기 위한 작업
                lp.topMargin = 10;
                lo.setLayoutParams(lp);
                searchText.clearFocus();
                // 배경화면 클릭 시 키보드 창을 없앤다.
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
            }
        });

        // 검색창 클릭 시 나타나는 리스너
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 키보드의 검색, 다음, 완료 버튼을 누른다면
                if((actionId == EditorInfo.IME_ACTION_DONE) || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_NEXT)){

                    LinearLayout lo = (LinearLayout)vview.findViewById(R.id.chickenlayout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT, 350);
                    lo.setLayoutParams(lp);
                    // 입력된 내용을 받아와서
                    String sName = searchText.getText().toString();
                    mListView = (ListView)vview.findViewById(R.id.listView);

                    // tasteView
                    ImageView tView = vview.findViewById(R.id.tasteView);
                    LinearLayout.LayoutParams tving = (LinearLayout.LayoutParams)tView.getLayoutParams();
                    // 화면 조정을 위한 로직. 검색 버튼을 누른 적이 없다면
                    if(isSearch == false) {
                        // <동구의맛> 위젯 크기를 줄인다
                        tving.weight = (float) (tving.weight * 0.5);
                        tving.height = (int) (tving.height * 0.5);
                        isSearch = true;
                    }
                    tView.setLayoutParams(tving);

                    LinearLayout lo2 = (LinearLayout)vview.findViewById(R.id.tastelayout);
                    FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams)lo2.getLayoutParams();

                    lp2.topMargin = 10;
                    lo2.setLayoutParams(lp2);
                    // 입력 데이터를 통한 검색 메소드
                    searching_data(sName);
                    dataSetting();
                }
                return false;
            }
        });
        // 단순 위젯 위치 조정 작업
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
            if(T.getStoreName() == null) continue;
            // 객체 리스트를 돌며 부분이라도 일치한다면 addList에 검색 결괏값을 추가한다.
            if(T.getStoreName().contains(pattern)){
                addList.add(T);
            }
        }
    }

    private void dataSetting(){
        MyAdapter mMyAdapter = new MyAdapter();
        // 검색결과인 addList 값을 어댑터에 추가한다.
        for (Info d : addList){
            mMyAdapter.addItem(d.getStoreAddr(), d.getStoreAddr2(), d.getStoreName(), d.getStoreHash(), d.getStoreTell(), d.getStoreType());
        }
        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }
}
