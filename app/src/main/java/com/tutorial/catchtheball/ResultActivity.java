package com.tutorial.catchtheball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        //MainActivity.javaで設定したキーを用いてスコアを取得
        int score = getIntent().getIntExtra("SCORE", 0);
        //スコアを表示
        scoreLabel.setText(score + "");

        //スコアを保存する処理（SharedPreferences）
        //getSharedPreferences(データ名, モード)
        //MODE_WORLD_READABLE
        //MODE_WORLD_WRITEABLE
        //MODE_MULTI_PROCESS
        //これらは他のアプリからも読み書きできるが、今回はこのアプリのみで使えればいいのでPRIVATE
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        //highscoreを取り出す getInt(キー, データがなかった場合の数値)
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if(score > highScore){
            //最新のスコアが既存のスコアを超えた場合は最新のスコアを表示し、セーブ
            highScoreLabel.setText("High Score : " + score);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            //putInt(キー, 保存する値)で保存
            //読み出し時・書き込み時のキーは必ず合わせる
            editor.putInt("HIGH_SCORE", score);
            //applyで保存
            editor.apply();
        }else{
            //ハイスコアの表示
            highScoreLabel.setText("High Score : " + highScore);
        }
    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //backを押しても反応しないようにコメントアウト
    }
}
