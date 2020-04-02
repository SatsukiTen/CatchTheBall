package com.tutorial.catchtheball;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    //位置
    private float boxY;

    //Handler & Timer
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Status
    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //親クラスのonCreateを呼び出す。
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idからビューを見つけて変数に代入
        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);

        boxY = 500.0f;
    }
    public void changePos(){
        if (action_flg){
            boxY -= 20;
        }else {
            boxY += 20;
        }
        box.setY(boxY);
    }

    //code→overridemethodから検索可能
    @Override //上書き処理
    public boolean onTouchEvent(MotionEvent event) {
        if (start_flg){
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if (event.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }else{
            start_flg = true;
            //Invisibleは非表示にする。GONEは完全に消すという違い。
            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
        return true;
    }
}