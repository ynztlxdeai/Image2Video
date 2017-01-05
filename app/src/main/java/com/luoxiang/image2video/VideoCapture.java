package com.luoxiang.image2video;

import android.os.Handler;
import android.os.Message;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameRecorder.Exception;
import com.googlecode.javacv.cpp.opencv_core;

import java.io.File;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
/**
 * packageName:	    com.luoxiang.image2video
 * className:	    VideoCapture
 * author:	        Luoxiang
 * time:	        2017/1/5	11:17
 * desc:	        视频生成类
 *
 * svnVersion:
 * upDateAuthor:    Vincent
 * upDate:          2017/1/5
 * upDateDesc:      TODO
 */


public class VideoCapture {
    private static int     switcher = 0;//录像键
    private static boolean isPaused = false;//暂停键
    private static String  filename = null;
    private static OnFinishListener mFinishListener;

    public static void start(final String path) {

        switcher = 1;
        new Thread() {
            public void run() {

                try {
                    filename = "test.mp4";
                    /*String dirPath = Environment.getExternalStorageDirectory()
                                                .getAbsolutePath() + File.separator + "magic" + File.separator + "screenshoot";*/


                    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(new File(path,
                                                                                    filename),
                                                                           320,
                                                                           240);

                    recorder.setFormat("mp4");
                    recorder.setFrameRate(2f);//录像帧率
                    recorder.start();
                    File   file     = new File(path);
                    File[] files    = file.listFiles();
                    //由于生成的文件放在这个目录 图片数量会少一张
                    int    length   = files.length - 1;
                    int    position = 0;
                    while (switcher != 0) {
                        if (!isPaused) {

                            if (position < length) {
                                opencv_core.IplImage image = cvLoadImage(new File(path,
                                                                                  (position++) + ".jpg").getAbsolutePath());
                                recorder.record(image);
                            } else {
                                recorder.stop();
                                switcher = 0;
                                mHandler.sendEmptyMessage(0);
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void setFinishListener(OnFinishListener finishListener) {
        mFinishListener = finishListener;
    }

    public static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                        case 0:
                            if (mFinishListener != null){
                                mFinishListener.OnFinish();
                            }
                            break;

                        default:
                            break;
                    }
        }
    };
}
