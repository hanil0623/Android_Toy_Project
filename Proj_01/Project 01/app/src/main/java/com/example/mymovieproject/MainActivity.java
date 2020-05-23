package com.example.mymovieproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_thumbup;
    ImageButton btn_thumbdown;
    TextView upview;
    TextView downview;
    TextView story;
    final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog/logfile.txt";
    boolean isselected1 = false;
    boolean isselected2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("씨네마천국");
        readFile();
        RatingBar rBar = (RatingBar)findViewById(R.id.RatingBar);
        final TextView rView = (TextView)findViewById(R.id.RatingView);


        btn_thumbup = (ImageButton)findViewById(R.id.thumb_up);
        btn_thumbdown = (ImageButton)findViewById(R.id.thumb_down);
        upview = (TextView)findViewById(R.id.thumb_up_text);
        downview = (TextView)findViewById(R.id.thumb_down_text);
        story = (TextView)findViewById(R.id.story);

        //recycle_view();
        rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rView.setText(""+rating);
            }
        });

        btn_thumbup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String vtext = upview.getText().toString();
                String vtext2 = downview.getText().toString();
                int a = Integer.parseInt(vtext);
                int b = Integer.parseInt(vtext2);
                if(!isselected1 && !isselected2) {
                    a++;
                    isselected1 = true;
                    upview.setText(String.valueOf(a));
                    btn_thumbup.setImageResource(R.drawable.ic_thumb_up_selected);
                }
                else{
                    if(isselected1 && !isselected2) {
                        if (a > 0) {
                            a--;
                        }
                        isselected1 = false;
                        upview.setText(String.valueOf(a));
                        btn_thumbup.setImageResource(R.drawable.ic_thumb_up);
                    }
                    else if(!isselected1 && isselected2){
                        if(b > 0){  b--; }
                        isselected2 = false;
                        downview.setText(String.valueOf(b));
                        btn_thumbdown.setImageResource(R.drawable.ic_thumb_down);
                    }
                }
            }
        });

        btn_thumbdown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String vtext = downview.getText().toString();
                String vtext2 = upview.getText().toString();
                int a = Integer.parseInt(vtext);
                int b = Integer.parseInt(vtext2);
                if(!isselected1 && !isselected2) {
                    a++;
                    isselected2 = true;
                    downview.setText(String.valueOf(a));
                    btn_thumbdown.setImageResource(R.drawable.ic_thumb_down_selected);
                }
                else{
                    if(isselected2 && !isselected1) {
                        if (a > 0) {
                            a--;
                        }
                        isselected2 = false;
                        downview.setText(String.valueOf(a));
                        btn_thumbdown.setImageResource(R.drawable.ic_thumb_down);
                    }
                    else if(isselected1 && !isselected2){
                        isselected1 = false;
                        if(b > 0){  b--; }
                        upview.setText(String.valueOf(b));
                        btn_thumbup.setImageResource(R.drawable.ic_thumb_up);
                    }
                }
            }
        });
    }

    /*
    public void recycle_view(){
        RecyclerView recyview = (RecyclerView)findViewById(R.id.recyclerView);
        // 세로방향으로 정해지는 recyclerview
        LinearLayoutManager lmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyview.setLayoutManager(lmanager);
    }*/

    public void readFile() {
        try {
            // getResources().openRawResource()로 raw 폴더의 원본 파일을 가져온다.
            // txt 파일을 InpuStream에 넣는다. (open 한다)
            InputStream in = getResources().openRawResource(R.raw.gundo);

            if (in != null) {
                InputStreamReader stream = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(stream);

                String read;
                StringBuilder sb = new StringBuilder("");

                while ((read = buffer.readLine()) != null) {
                    sb.append(read);
                    sb.append('\n');
                }
                in.close();
                // id : textView01 TextView를 불러와서
                //메모장에서 읽어온 문자열을 등록한다.
                TextView textView = (TextView) findViewById(R.id.story);
                textView.setText(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
