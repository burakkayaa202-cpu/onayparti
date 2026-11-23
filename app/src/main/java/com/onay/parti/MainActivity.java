package com.onay.parti;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;
    private static final String SITE = "https://onayparti.blogspot.com/";

    private static final String OFFLINE_HTML =
        "<html><head><meta name='viewport' content='width=device-width, initial-scale=1'></head>"
        + "<body style='font-family:sans-serif;text-align:center;padding:80px 24px;color:#1F4E8C'>"
        + "<h2>İnternet bağlantısı yok</h2>"
        + "<p>Bağlantını kontrol edip tekrar dene.</p>"
        + "<a href='" + SITE + "' style='display:inline-block;margin-top:24px;padding:14px 32px;"
        + "background:#1F4E8C;color:#fff;border-radius:8px;text-decoration:none'>Tekrar dene</a>"
        + "</body></html>";

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        web = new WebView(this);
        web.setBackgroundColor(Color.WHITE);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                Uri u = r.getUrl();
                String host = u.getHost() == null ? "" : u.getHost();
                if (host.endsWith("onayparti.blogspot.com")) {
                    return false;
                }
                startActivity(new Intent(Intent.ACTION_VIEW, u));
                return true;
            }

            @Override
            public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
                if (r.isForMainFrame()) {
                    v.loadDataWithBaseURL(null, OFFLINE_HTML, "text/html", "UTF-8", null);
                }
            }
        });
        if (b != null) web.restoreState(b);
        else web.loadUrl(SITE);
        setContentView(web);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        web.saveState(out);
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }
}
