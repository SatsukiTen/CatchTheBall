package com.tutorial.catchtheball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
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

    //サイズ
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    //位置
    private float boxY;
    private float orangeX;
    private float orangeY;
    private float pinkX;
    private float pinkY;
    private float blackX;
    private float blackY;

    //Score
    private int score = 0;

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

        //Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay(); //アプリの外枠のサイズが取得可能
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);

        scoreLabel.setText("score : 0");
    }
    public void changePos(){

        //前状態で衝突していたか否かを
        hitCheck();

        //Orange
        orangeX -= 12; //速度
        if(orangeX < 0){
            orangeX = screenWidth + 20; //0以下の時に画面右に移動
            orangeY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));//0<1の乱数生成
        }

        //更新
        orange.setX(orangeX);
        orange.setY(orangeY);

        //Black
        blackX -= 16;
        if(blackX < 0){
            blackX = screenWidth + 10;
            blackY = (float)Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //Pink
        pinkX -= 20;
        if(pinkX < 0){
            pinkX = screenWidth + 5000;
            pinkY = (float)Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        //Box
        if (action_flg){
            boxY -= 20;
        }else {
            boxY += 20;
        }

        if(boxY < 0) boxY = 0;

        //frameの高さからboxの高さを引いた値
        //スマホでは左上の座標が0となる。
        //相対距離を使用しているため端末サイズに基づくバグが消失する。
        if(boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLabel.setText("Score : " + score);

    }

    public void hitCheck(){
        //Orange
        float orangeCenterX = orangeX + orange.getWidth() / 2;
        float orangeCenterY = orangeY + orange.getHeight() / 2;

        //ボールの中心座標が青いボックスの中に入ったか否か
        if (hitStatus(orangeCenterX, orangeCenterY)){

            orangeX = -10.0f;
            score += 10;
        }

        //Pink
        float pinkCenterX = pinkX + pink.getWidth() / 2;
        float pinkCenterY = pinkY + pink.getHeight() / 2;

        if (hitStatus(pinkCenterX, pinkCenterY)){

            pinkX = -10.0f;
            score += 30;
        }

        //Black
        float blackCenterX = blackX + black.getWidth() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

        if (hitStatus(blackCenterX, blackCenterY)){

            //GameOver
            if(timer != null){
                //timerを停止する→timerで管理していたもの全て停止
                timer.cancel();
                timer = null;
            }

            //結果画面へ
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);//intentで指定したスクリーンを開始する。
            intent.putExtra("SCORE", score);//putExtra("取り出すときに使うキー, 渡したい値)
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //backを押しても反応しないようにコメントアウト
    }

    public boolean hitStatus(float centerX, float centerY){
        return (0 <= centerX && centerX <= boxSize && boxY <= centerY && centerY <= boxY + boxSize) ? true : false;
    }

    //code→overridemethodから検索可能
    @Override //上書き処理
    public boolean onTouchEvent(MotionEvent event) {
        if (start_flg == false){
            start_flg = true;

            //onCreateではビューの描画が完了していないため、確実に完了しているonTouchEventでサイズを取得
            //onCreateが終了した時点でビューの描画は完了する。
            //FrameLayoutは要素をはめるための枠組みなので、初期値としてHeightなどは存在していない。
            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = box.getY();
            boxSize = box.getHeight();

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
        }else{
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if (event.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return true;
    }
}