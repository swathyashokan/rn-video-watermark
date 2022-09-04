package me.vyoo;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.daasuu.mp4compose.composer.Mp4Composer;
import com.daasuu.mp4compose.filter.GlWatermarkFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.graphics.BitmapFactory;

public class VideoWatermarkModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public VideoWatermarkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "VideoWatermark";
    }

    @ReactMethod
    public void convert(String videoPath, String imagePath, String watermarkPosition, Callback callback) {
        watermarkVideoWithImage(videoPath, imagePath, watermarkPosition, callback);
    }

    public void watermarkVideoWithImage(String videoPath, String imagePath, String watermarkPosition, final Callback callback) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File destFile = new File(this.getReactApplicationContext().getFilesDir(), timestamp.getTime() + ".mp4");
    //    File destFile = new File(Environment.getExternalStorageDirectory().getPath(), "converted.mp4");
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final String destinationPath = destFile.getPath();
        GlWatermarkFilter.Position wtrkMrkPos;
        switch (watermarkPosition) {

            case "LEFT_TOP":
                wtrkMrkPos = GlWatermarkFilter.Position.LEFT_TOP;
                break;

            case "LEFT_BOTTOM":
                wtrkMrkPos = GlWatermarkFilter.Position.LEFT_BOTTOM;
                break;

            case "RIGHT_TOP":
                wtrkMrkPos = GlWatermarkFilter.Position.RIGHT_TOP;
                break;

            case "RIGHT_BOTTOM":
                wtrkMrkPos = GlWatermarkFilter.Position.RIGHT_BOTTOM;
                break;

            default:
                wtrkMrkPos = GlWatermarkFilter.Position.LEFT_TOP;
                break;

        }

        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(imagePath);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        GlWatermarkFilter glWatermarkFilter = new GlWatermarkFilter(bitmap, wtrkMrkPos);
        new Mp4Composer(videoPath, destinationPath)
                .filter(glWatermarkFilter)
                .listener(new Mp4Composer.Listener() {
                    @Override
                    public void onProgress(double progress) {
                        Log.e("Progress", progress + "");
                    }

                    @Override
                    public void onCompleted() {
                        callback.invoke(destinationPath);
                    }

                    @Override
                    public void onCanceled() {
                        callback.invoke("cancelled");
                    }

                    @Override
                    public void onFailed(Exception exception) {
                        callback.invoke("failed");
                        exception.printStackTrace();
                    }
                }).start();
    }


}
