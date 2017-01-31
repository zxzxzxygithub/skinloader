package cn.feng.skin.demo;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Admin on 2017/1/31.
 */

public class FileUtils {

    public static void copyAssetDirToFiles(Context context, String dirname)
            throws IOException {
        File dir = new File(context.getFilesDir() + "/" + dirname);
        dir.mkdir();

        AssetManager assetManager = context.getAssets();
        String[] children = assetManager.list(dirname);
        for (String child : children) {
            child = dirname + '/' + child;
            String[] grandChildren = assetManager.list(child);
            if (0 == grandChildren.length)
                copyAssetFileToFiles(context, child);
            else
                copyAssetDirToFiles(context, child);
        }
    }

    public static void copyAssetFileToFiles(Context context, String filename) {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = context.getAssets().open(filename);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            File of = new File(context.getFilesDir() + "/" + filename);
            of.createNewFile();

            os = new FileOutputStream(of);
            os.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
