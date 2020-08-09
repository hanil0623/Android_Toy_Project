package com.example.clone_project_01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
/* DB 관리자 화면으로 넘어가기 전, 4초에 7번 이상 클릭 하는 Fragment */
public class Fragment3 extends Fragment {
    // 임시로 설정한 비밀번호
    private final String PASSKEY = "jihunsihyungzzang";
    ImageButton shield;
    ArrayList<Info> data;
    int buttonclick = 0;
    long start = 0;
    long end = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buttonclick = 0; // 클릭 횟수 초기화
        return inflater.inflate(R.layout.fragment3, container, false);
    }

    /* 4초에 7번이상 클릭 해야 DB 관리 화면으로 들어갈 수 있는 로직 */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        shield = (ImageButton)view.findViewById(R.id.shield_off_button);
        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 처음 클릭 이벤트 발생 시 시작 시간 기록
                if(buttonclick == 0){
                    start = System.currentTimeMillis();
                }
                // 7번이상 클릭하면
                else if(buttonclick >= 7){
                    // 끝나는 시간값 기록
                    end = System.currentTimeMillis();
                    // 비교하여 4초 이하라면 비밀번호 입력 창 실행
                    if(end - start <= 4000){
                        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                        final EditText et = new EditText(getContext());
                        ad.setView(et);
                        ad.setTitle("Security Check");
                        ad.setMessage("비밀번호 입력하세요");
                        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = et.getText().toString();
                                //Toast.makeText(getContext(), "선택: " + value, Toast.LENGTH_SHORT).show(); // 디버깅을 위한 출력

                                // 비밀번호가 일치한다면 SettingActivity 실행
                                if(value.equals(PASSKEY)){
                                    Toast.makeText(getContext(), "지훈 x 시형 = 짱짱맨", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                                    startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        });
                        // BACK 버튼 누를 시 dialog 사라짐
                        ad.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.show();
                    } else {
                        // 7번 클릭 시간이 4초가 넘어간다면 시작시간, 버튼 클릭 회수 초기화
                        start = 0;
                        buttonclick = -1;
                    }
                }
                buttonclick++;
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
