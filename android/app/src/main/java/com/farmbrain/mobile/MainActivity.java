package com.farmbrain.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.view.inputmethod.InputType;
import android.webkit.*;
import android.widget.*;
import android.content.ActivityNotFoundException;

public class MainActivity extends Activity {

    private WebView web;
    private ProgressBar progress;
    private SharedPreferences prefs;
    private String website;
    private ValueCallback<Uri[]> fileCallback;
    private static final int FILE_REQUEST = 101;

    private int dp(int v) {
        return (int)(v * getResources()
            .getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(23,61,43));
        getWindow().setNavigationBarColor(Color.rgb(16,45,32));

        prefs = getSharedPreferences("farmbrain", MODE_PRIVATE);
        website = prefs.getString(
            "website",
            getString(R.string.website_url)
        );

        createScreen();
        configureBrowser();
        openWebsite();
    }

    private void createScreen() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.WHITE);

        LinearLayout bar = new LinearLayout(this);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setPadding(dp(15), 0, dp(10), 0);
        bar.setBackgroundColor(Color.rgb(23,61,43));

        TextView title = new TextView(this);
        title.setText("FARM BRAIN");
        title.setTextColor(Color.WHITE);
        title.setTextSize(19);
        title.setTypeface(null, 1);

        bar.addView(title, new LinearLayout.LayoutParams(
            0, dp(55), 1
        ));

        Button settings = new Button(this);
        settings.setText("Website");
        settings.setOnClickListener(v -> changeWebsite());
        bar.addView(settings);

        Button reload = new Button(this);
        reload.setText("↻");
        reload.setOnClickListener(v -> openWebsite());
        bar.addView(reload);

        root.addView(bar);

        progress = new ProgressBar(
            this, null,
            android.R.attr.progressBarStyleHorizontal
        );
        progress.setMax(100);
        root.addView(progress, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(3)
        ));

        web = new WebView(this);
        root.addView(web, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            0, 1
        ));

        setContentView(root);
    }

    private void configureBrowser() {
        WebSettings s = web.getSettings();

        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(true);
        s.setLoadsImagesAutomatically(true);
        s.setMixedContentMode(
            WebSettings.MIXED_CONTENT_NEVER_ALLOW
        );

        CookieManager cookies = CookieManager.getInstance();
        cookies.setAcceptCookie(true);
        cookies.setAcceptThirdPartyCookies(web, true);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(
                WebView view,
                WebResourceRequest request
            ) {
                Uri uri = request.getUrl();
                Uri base = Uri.parse(website);

                if ("https".equalsIgnoreCase(uri.getScheme())
                    && uri.getHost() != null
                    && uri.getHost().equalsIgnoreCase(base.getHost())) {
                    return false;
                }

                try {
                    startActivity(new Intent(
                        Intent.ACTION_VIEW, uri
                    ));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this,
                        "Cannot open this link",
                        Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public void onPageStarted(
                WebView view, String url,
                android.graphics.Bitmap icon
            ) {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(
                WebView view, String url
            ) {
                progress.setVisibility(View.GONE);
            }
        });

        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(
                WebView view, int value
            ) {
                progress.setProgress(value);
                progress.setVisibility(
                    value >= 100 ? View.GONE : View.VISIBLE
                );
            }

            @Override
            public boolean onShowFileChooser(
                WebView view,
                ValueCallback<Uri[]> callback,
                FileChooserParams params
            ) {
                if (fileCallback != null) {
                    fileCallback.onReceiveValue(null);
                }

                fileCallback = callback;
                Intent intent = params.createIntent();

                try {
                    startActivityForResult(
                        intent, FILE_REQUEST
                    );
                    return true;
                } catch (ActivityNotFoundException e) {
                    fileCallback = null;
                    return false;
                }
            }
        });
    }

    private void openWebsite() {
        Uri uri = Uri.parse(website);

        if (!"https".equalsIgnoreCase(uri.getScheme())
            || uri.getHost() == null) {
            Toast.makeText(this,
                "Invalid HTTPS website address",
                Toast.LENGTH_LONG).show();
            return;
        }

        web.loadUrl(website);
    }

    private void changeWebsite() {
        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setInputType(
            InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_URI
        );
        input.setText(website);

        new AlertDialog.Builder(this)
            .setTitle("FARM BRAIN website")
            .setMessage("Enter your live HTTPS website URL")
            .setView(input)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save", (d, w) -> {
                String value = input.getText()
                    .toString().trim();

                Uri uri = Uri.parse(value);

                if (!"https".equalsIgnoreCase(uri.getScheme())
                    || uri.getHost() == null) {
                    Toast.makeText(this,
                        "Enter a valid HTTPS URL",
                        Toast.LENGTH_LONG).show();
                    return;
                }

                website = value;
                if (!website.endsWith("/")) {
                    website += "/";
                }

                prefs.edit()
                    .putString("website", website)
                    .apply();

                web.clearHistory();
                openWebsite();
            })
            .show();
    }

    @Override
    protected void onActivityResult(
        int requestCode, int resultCode, Intent data
    ) {
        super.onActivityResult(
            requestCode, resultCode, data
        );

        if (requestCode == FILE_REQUEST
            && fileCallback != null) {

            Uri[] result = null;

            if (resultCode == RESULT_OK && data != null
                && data.getData() != null) {
                result = new Uri[]{data.getData()};
            }

            fileCallback.onReceiveValue(result);
            fileCallback = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (fileCallback != null) {
            fileCallback.onReceiveValue(null);
            fileCallback = null;
        }

        if (web != null) {
            web.destroy();
        }

        super.onDestroy();
    }
}
