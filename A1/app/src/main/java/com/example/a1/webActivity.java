package com.example.a1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class webActivity extends Activity {

    private WebView a1view;
    public static String[] vacationSites;

    public Intent intent;
    public static final String INDEX_WEB = "IDXW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        //populate url array
        vacationSites = getResources().getStringArray(R.array.vacationUrl);

        //Get Intent that was passed from the receiver
        intent = getIntent();

        //Get the index from the receiver
        int index = intent.getIntExtra(INDEX_WEB, 0);

        //The index will determine which vacation location website is displayed
        a1view = (WebView) findViewById(R.id.a1webview);
        a1view.setWebViewClient(new WebViewClient());
        a1view.loadUrl(vacationSites[index]);

    }

    @Override
    public void onBackPressed() {
        //Will let the user go back on web pages first rather than activities
        if(a1view.canGoBack()) {
            a1view.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
