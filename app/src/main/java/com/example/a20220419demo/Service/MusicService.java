package com.example.a20220419demo.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.a20220419demo.EighthActivity;

import java.io.IOException;


public class MusicService extends Service {
    public final IBinder binder = new MyBinder();

    private Uri uri;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        uri = Uri.parse(intent.getStringExtra("uri"));
        init();
        return binder;
    }

    public class MyBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    //    音乐init
    public static MediaPlayer mp = new MediaPlayer();

    public MusicService() {

    }
    public void init(){
        try {
//            System.out.println("22222"+path);
//            mp = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/music140"));
            mp.setDataSource(this,uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    播放
    public void play(){
        mp.start();
    }
    //    停止
    public void stop() throws IOException {
        if (mp != null){
            mp.stop();
            mp.prepare();
            mp.seekTo(0);
        }
    }

}
