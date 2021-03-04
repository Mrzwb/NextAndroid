package com.zwb.next.application.systemui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import static android.view.ViewGroup.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

/**
 * @author zhouwb 2021-03-04
 */
public class WebViewDirector {

    private static final String TAG = WebViewDirector.class.getSimpleName();

    @NonNull
    private WebView mWebView;

    private Context mContext;

    private ValueCallback<Uri[]> mFilePathCallback;

    private static final int FILE_CHOOSER_RESULT_CODE = 1000;

    private WebViewDirector(@NonNull Context context) {
        mContext = context;
        mWebView = new WebView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(layoutParams);
    }

    public static WebViewDirector newInstance(final Context context) {
        return new WebViewDirector(context);
    }

    public WebView buildDefaultWebView() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(false);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        setAcceptThirdPartCookies();
        setWebClient();
        setWebChromeClient();
        return mWebView;
    }

    public WebView buildCustomerWebView(IBuilder iBuilder) {
        iBuilder.setWebSetting(mWebView);
        iBuilder.setWebClient(mWebView);
        iBuilder.setWebChromeClient(mWebView);
        return mWebView;
    }

    private void setAcceptThirdPartCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        }
    }

    private void setWebClient() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    private void setWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
                AlertDialog.Builder alert = new AlertDialog.Builder(webView.getContext());
                alert.setMessage(message).setPositiveButton("确定",null);
                alert.setCancelable(false);
                alert.create().show();
                result.confirm();
                return true;
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG,  "加载进度: " + newProgress);
            }

            //For Android  >= 5.0
            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;
                if (mContext instanceof Activity) {
                    openFileChooser((Activity)mContext);
                }
                return true;
            }
        });
    }

    private void openFileChooser(final Activity activity) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(i, "选择文件"), FILE_CHOOSER_RESULT_CODE);
    }

    /**
     * 文件选择回调
     * <pre> e.g:
     *    WebViewDirector webViewDirector = WebViewDirector.newInstance(Activity.this);
     *    <code>@Override</code>
     *    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     *        super.onActivityResult(requestCode, resultCode, data);
     *        webViewDirector.onActivityResultForFileChooser(requestCode, resultCode, data);
     *    }
     * </pre>
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResultForFileChooser (int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            if (mFilePathCallback != null && data != null) {
                Uri[] results = null;
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            }
        } else {
            if (mFilePathCallback !=null) {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        }
    }

    /**
     * 释放webView
     * <pre>
     *     WebViewDirector webViewDirector = WebViewDirector.newInstance(Activity.this);
     *     <code>@Override</code>
     *     protected void onDestroy() {
     *         super.onDestroy();
     *         mWebViewDirector.clearWebView();
     *     }
     * </pre>
     */
    public void clearWebView() {
        if (mWebView != null) {
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public interface IBuilder {

        void setWebSetting(WebView webView);

        void setWebClient(WebView webView);

        void setWebChromeClient(WebView webView);

    }
}
