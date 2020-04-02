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

        startLabel.setVisibility(View.INVISIBLE);
        boxY = 500.0f;

        //schedule(TimerTask task, long delay, long period)
        //task:実行する処理（タスク）
        //delay:タスクを実行するまでに待機する時間（ミリ秒で指定）
        //period:タスクの実行間隔（ミリ秒で指定）
        //この設定だと、「待機時間なし、20ミリ秒毎にタスクを実行する」となる。
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                //<Timer task
                //メインスレッド外でUIを変更することはできない（Timer taskからはスコアラベルを更新できない）
                //ので、handler.postでUIスレッドにrunnnableを渡してUIを更新する。
                handler.post(new Runnable() {
                    @Override
                    public void run(){
                        changePos();//画像の位置を変更＆スコアラベルを更新
                    }
                });
                //Timer task>
            }
        }, 0,20);

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
        if (event.getAction() == MotionEvent.ACTION_DOWN){//画面をタップした場合の判定
            action_flg = true;
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            action_flg = false;
        }
        return true;
    }
}