package com.zwb.next.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import static android.view.ViewGroup.*;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.zwb.next.application.systemui.WebViewDirector;

public class WebViewActivity  extends Activity {

    private WebViewDirector mWebViewDirector;

    private WebView mWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("http://www.baidu.com");
            }
        });
    }

    protected void initView() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(layoutParams);
        mWebViewDirector = WebViewDirector.newInstance(this);
        mWebView = mWebViewDirector.buildDefaultWebView();
        linearLayout.addView(mWebView);
        setContentView(linearLayout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mWebViewDirector.onActivityResultForFileChooser(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebViewDirector.clearWebView();
    }

}
