package com.zwb.next.util;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.XmlRes;

/**
 * @author zhouwb
 */
public class AssetsUtils {

    private static final String CHARSET = "UTF-8";

    public static InputStream getFile(Context context, String fileName) {
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static XmlResourceParser getResXml(Context context, @XmlRes int id) {
        XmlResourceParser xmlResourceParser = null;
        try {
            xmlResourceParser = context.getResources().getXml(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlResourceParser;
    }

    public static String getString(Context context, String fileName) {
        String data = "";
        InputStream is = getFile(context, fileName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while((len=is.read(buffer))!=-1) {
                bos.write(buffer, 0, len);
            }
            data = bos.toString(CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(is);
            closeStream(bos);
        }
        return data;
    }

    public static Bitmap getBitmap(Context context, String fileName) {
        InputStream is = getFile(context, fileName);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    private static void closeStream(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
