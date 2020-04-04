package com.tutorial.catchtheball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer{

    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;

    private AudioAttributes audioAttributes;

    public SoundPlayer(Context context){
        //ゲームの効果音など短い音を再生するときはSoundPoolクラス
        // maxStreamsは最大同時音声数
        // STREAM_MUSICは音声ファイルの種類
        //srcQualityは再生品質（デフォルトが0）
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        //音声ファイルの読み込み
        //R.raw.correctは音声ファイルのID
        //priorityは再生品質（デフォルト1）
        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);
    }

    //ヒット音の再生メソッド
    //play(int soundID, float leftVolume（左の音量）, float rightVolume（右の音量）, int priority（優先度）, int loop（繰り返し 0:する　1:しない）, float rate(再生速度))
    public void playHitSound(){
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playOverSound(){
        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}