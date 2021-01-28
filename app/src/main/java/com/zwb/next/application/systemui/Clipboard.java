package com.zwb.next.application.systemui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 剪切板操作
 *
 * @author zhouwb
 */
public class Clipboard {

    public static void clipFromText(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    public static void clipFromUri(Context context, String label, Uri copyUri) {
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newUri(context.getContentResolver(), label, copyUri);
        clipboard.setPrimaryClip(clip);
    }


    public static void clipFromIntent(Context context, String label, Intent appIntent) {
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newIntent(label, appIntent);
        clipboard.setPrimaryClip(clip);
    }

    public static void clearClips(Context context) {
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.clearPrimaryClip();
    }

    //TODO

}
