package com.diamond.SmartVoice.Recognizer;

import android.media.MediaPlayer;
import android.util.Log;

import com.diamond.SmartVoice.MainActivity;
import com.diamond.SmartVoice.Utils;

import java.io.File;
import java.io.IOException;

import ai.kitt.snowboy.RecordingThread;

public class SnowboyRecognizer extends AbstractRecognizer {

    private MainActivity mContext;

    private static final String TAG = SnowboyRecognizer.class.getSimpleName();

    private RecordingThread recordingThread;

    public SnowboyRecognizer(MainActivity context) {
        this.mContext = context;
        recordingThread = new RecordingThread(new ResultListener(), "alexa.umdl", "" + (Integer.parseInt(context.pref.getString("SnowboySensitivity", "60")) * 1f / 100f));
    }

    public void startListening() {
        recordingThread.startRecording();
        Log.d(TAG, " ----> recording started ...");
    }

    public void stopListening() {
        recordingThread.stopRecording();
        Log.d(TAG, " ----> recording stopped ");
    }

    public void destroy() {
        recordingThread.stopRecording();
    }

    public class ResultListener {
        public void onResult() {
            if (mContext.recognizer instanceof YandexRecognizer)
                Utils.ding.start();
            mContext.OnKeyPhrase();
        }
    }
}