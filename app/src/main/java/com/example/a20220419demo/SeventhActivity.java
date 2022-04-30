package com.example.a20220419demo;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Timer;
import java.util.TimerTask;

public class SeventhActivity extends Activity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private EditText editText = null;
    private Button download = null;
    private Button stop = null;
    private Button play = null;
    private ProgressBar downloadProgress = null;
    private ProgressBar playProgress = null;
    private TextView times = null;
    private Thread thread;
    private Thread thread2;

    boolean flag = true;
    private Handler handler;
    private static MediaPlayer mediaPlayer = null;
    private int iTimes = 0;
    private long Id = -99;
    private DownloadManager downloadManager;
    private Timer timer;
    private Timer timer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);
        verifyStoragePermissions(this);

        editText = findViewById(R.id.web);
        download = findViewById(R.id.download);
        stop = findViewById(R.id.stop);
        downloadProgress = findViewById(R.id.downloadProgress);
        playProgress = findViewById(R.id.playProgress);
        times = findViewById(R.id.times);
        play = findViewById(R.id.play);

        timer2 = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                downloadListen();
            }
        };
        timer2.schedule(timerTask, 0, 10);

        editText.setText("https://music.163.com/song/media/outer/url?id=1409311773.mp3");

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editText.getText().toString();
                if (!url.equals("") && url != null) {
                    if (flag) {
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                download();
                            }
                        });
                        thread.start();
                        flag = false;
                    } else {
                        Toast.makeText(SeventhActivity.this, "正在下载音乐", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SeventhActivity.this, "请输入下载地址", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopmusic();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playmusic();
            }
        });

    }

    private void downloadListen() {
        try {
            if (Id != -99) {
                DownloadManager.Query downloadQuery = new DownloadManager.Query();
                downloadQuery.setFilterById(Id);
                Cursor cursor = downloadManager.query(downloadQuery);

                if (cursor != null && cursor.moveToFirst()) {
                    int totalSizeBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                    int bytesDownloadSoFarIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                    // 下载的文件总大小
                    int totalSizeBytes = cursor.getInt(totalSizeBytesIndex);
                    // 截止目前已经下载的文件总大小
                    int bytesDownloadSoFar = cursor.getInt(bytesDownloadSoFarIndex);
                    int progress = (bytesDownloadSoFar / totalSizeBytes) * 100;
                    downloadProgress.setProgress(progress);

                    if (downloadProgress.getProgress() == 100) {
                        timer2.cancel();
//                        Toast.makeText(SeventhActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载音乐
     */
    private void download() {
        try {
            String url = "https://music.163.com/song/media/outer/url?id=1409311773.mp3";
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/music140");
            int downloadStatus = -1;
            verifyStoragePermissions(this);
            if (!file.exists()) {
                //创建下载任务,downloadUrl就是下载链接
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                //指定下载路径和下载文件名
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, "music140");
                //获取下载管理器
                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                //将下载任务加入下载队列，否则不会进行下载
                Id = downloadManager.enqueue(request);
            }
//            playmusic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放音乐
     */
    private void playmusic() {
        verifyStoragePermissions(this);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();

            //千万不要在music后面加.mp3 ！！！！！！！！！！！！！！
            mediaPlayer = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/music140"));
            mediaPlayer.start();

            playProgress.setMax(mediaPlayer.getDuration());
            try {
                timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        playProgress.setProgress(mediaPlayer.getCurrentPosition());
                    }
                };
                timer.schedule(timerTask, 0, 10);
                iTimes++;
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    times.setText("" + iTimes);
                    playmusic();
                }
            });
        } else {
            mediaPlayer.start();
        }
    }

    /**
     * 停止音乐
     */
    private void stopmusic() {
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        timer2.cancel();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (thread != null) {
            thread = null;
        }
        super.onDestroy();
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
