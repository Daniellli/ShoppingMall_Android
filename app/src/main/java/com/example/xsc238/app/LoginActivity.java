package com.example.xsc238.app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xsc238.R;
import com.example.xsc238.view.CustomVideoView;

import java.io.File;

import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    private CustomVideoView videoView;
    private EditText editText;
    private EditText editText2;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-05-25 20:18:32 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        videoView = findViewById(R.id.videoView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViews();
        getSupportActionBar().hide();
        bg_video();
    }

    /**
     * 背景视频
     */
    private void bg_video() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String router = "android.resource://" + getPackageName() + File.separator + R.raw.bg;
        if (router != null) {
            videoView.setVideoURI(Uri.parse(router));
        } else {
            Toast.makeText(this, "要播放的视频不存在", Toast.LENGTH_SHORT).show();
        }
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
/**
 * 循环播放
 */
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
    }

    public void login(View view) {
        String account = editText.getText().toString();
        String pwd = editText2.getText().toString();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "password is wrong", Toast.LENGTH_SHORT).show();
        }

    }

    public void register(View view) {
        Toast.makeText(this, "regster", Toast.LENGTH_SHORT).show();
    }

    public void changePwd(View view) {
        Toast.makeText(this, "chane pwd", Toast.LENGTH_SHORT).show();
    }
}
