package com.example.a20220419demo;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.a20220419demo.Service.MusicService;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class EighthActivity extends Activity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private Button select, play, stop;
    private ProgressBar schedule;
    private TextView result;

    private String path;

    private MusicService musicService;
    private MusicService.MyBinder binder;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder) iBinder).getService();
            binder = (MusicService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth);
        verifyStoragePermissions(this);

        play = findViewById(R.id.music_play);
        stop = findViewById(R.id.music_stop);
        select = (Button) this.findViewById(R.id.music_select);
        schedule = findViewById(R.id.music_schedule);
        result = (TextView) this.findViewById(R.id.music_result);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(EighthActivity.this, MusicService.class);
//                bindService(intent, sc, EighthActivity.this.BIND_AUTO_CREATE);
                musicService.play();
                schedule.setMax(MusicService.mp.getDuration());
                Timer timer;
                timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        schedule.setProgress(MusicService.mp.getCurrentPosition());
                    }
                };
                timer.schedule(timerTask, 0, 10);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    musicService.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // 所有类型
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivityForResult
                    (Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String TAG = "ChooseFile";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        verifyStoragePermissions(this);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
//                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/music140");

                    Log.d(TAG, "File Uri: " + uri.toString());

//                    path = UriUtils.getPath(this, uri);
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/music140";

                    Log.d(TAG, "File Path: " + path);
                    result.setText("歌曲路径为" + path);
                    setPath(path);

                    Intent intent = new Intent(EighthActivity.this, MusicService.class);
                    intent.putExtra("uri", uri.toString());
                    bindService(intent, sc, EighthActivity.this.BIND_AUTO_CREATE);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    //动态获取内存存储权限
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
