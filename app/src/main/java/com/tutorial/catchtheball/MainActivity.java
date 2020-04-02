package com.tutorial.catchtheball;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    //位置
    private float boxY;

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
    }

    //code→overridemethodから検索可能
    @Override //上書き処理
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){//画面をタップした場合の判定
            boxY -= 20.0f;
        }
        box.setY(boxY);//boxの位置を上に移動させる。
        return true;
    }
}