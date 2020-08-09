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

public class Fragment3 extends Fragment {
    private final String PASSKEY = "jihunsihyungzzang";
    ImageButton shield;
    ArrayList<Info> data;
    int buttonclick = 0;
    long start = 0;
    long end = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buttonclick = 0;

        //Bundle bundle = getArguments();
        //data = bundle.getParcelableArrayList("gdata");
        return inflater.inflate(R.layout.fragment3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        shield = (ImageButton)view.findViewById(R.id.shield_off_button);
        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonclick == 0){
                    start = System.currentTimeMillis();
                }
                else if(buttonclick >= 7){
                    end = System.currentTimeMillis();
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
                                //Toast.makeText(getContext(), "선택: " + value, Toast.LENGTH_SHORT).show();
                                if(value.equals(PASSKEY)){
                                    Toast.makeText(getContext(), "지훈시형짱짱맨", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                                    //intent.putParcelableArrayListExtra("DB", data);
                                    startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        });
                        ad.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad.show();
                    } else {
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
