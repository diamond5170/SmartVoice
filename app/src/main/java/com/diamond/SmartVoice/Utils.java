package com.diamond.SmartVoice;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import edu.cmu.pocketsphinx.Assets;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static File assetDir;

    public static MediaPlayer ding = new MediaPlayer();
    public static MediaPlayer dong = new MediaPlayer();

    public static void load(MainActivity context) {
        try {
            Assets assets = new Assets(context);
            assetDir = assets.syncAssets();
            try {
                ding.setDataSource(new File(Utils.assetDir, "ding.wav").getAbsolutePath());
                dong.setDataSource(new File(Utils.assetDir, "dong.wav").getAbsolutePath());
                ding.prepare();
                dong.prepare();
            } catch (IOException e) {
                Log.e(TAG, "Playing ding sound error", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeStringToFile(final File file, final String data) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file);
            out.write(data.getBytes(Charset.forName("UTF8")));
            out.close();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (final IOException ignored) {
            }
        }
    }

    private static FileOutputStream openOutputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory())
                throw new IOException("File '" + file + "' exists but is a directory");
            if (!file.canWrite())
                throw new IOException("File '" + file + "' cannot be written to");
        } else {
            final File parent = file.getParentFile();
            if (parent != null)
                if (!parent.mkdirs() && !parent.isDirectory())
                    throw new IOException("Directory '" + parent + "' could not be created");
        }
        return new FileOutputStream(file, false);
    }
}