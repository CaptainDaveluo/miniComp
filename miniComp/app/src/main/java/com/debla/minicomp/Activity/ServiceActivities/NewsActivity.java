package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/4/8.
 */

public class NewsActivity extends Activity{
    private WebView wv_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);
        wv_news = (WebView) findViewById(R.id.news_wv);
        Intent it = getIntent();
        //wv_news.getSettings().setJavaScriptEnabled(true);
        //wv_news.addJavascriptInterface(new InJavaScriptLocalObj(),"local_obj");
        //wv_news.setWebViewClient(new MyWebViewClient());
        wv_news.loadUrl(it.getStringExtra("urlPath"));

    }
    /*
    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView","onPageStarted");
            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            Log.d("WebView","onPageFinished ");
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                    "document.getElementsByTagName('body')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
        }
    }*/
    /*final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            Log.e("HTML", html);
        }
    }*/
}
