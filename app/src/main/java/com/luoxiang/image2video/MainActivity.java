package com.luoxiang.image2video;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity
        extends AppCompatActivity
        implements OnFinishListener
{

    @Bind(R.id.main_btn_start)
    Button   mMainBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        VideoCapture.setFinishListener(this);
    }

    @OnClick(R.id.main_btn_start)
    public void onClick() {
        mMainBtnStart.setText("进行中");
        VideoCapture.start(Environment.getExternalStorageDirectory()
                                      .getAbsolutePath() + File.separator + "magic" + File.separator + "screenshoot");
    }

    @Override
    public void OnFinish() {
        mMainBtnStart.setText("开始");
        Toast.makeText(MainActivity.this , "生成完成" , Toast.LENGTH_LONG).show();
    }
}
