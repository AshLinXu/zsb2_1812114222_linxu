package com.lx22.zsb2_1812114222_linxu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsDetailActivity extends Activity {

    private int myTextSizeWich = 1;
    private int myCurrentTextSizeWich = 1;

    @ViewInject(R.id.webView)
    private WebView webView;

    @ViewInject(R.id.imb_back)
    private ImageButton backWord;

    @ViewInject(R.id.pb_loading)
    private ProgressBar loadingPB;

    @ViewInject(R.id.imb_setSize)
    private ImageButton im_setSize;

//    @ViewInject(R.id.imb_share)
//    private ImageButton im_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
//        //模拟器测试用
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        // ------
        ViewUtils.inject(this);

        String url = getIntent().getStringExtra("url");

        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
//        settings.setDisplayZoomControls(true);//放大缩小按钮
        settings.setUseWideViewPort(true);//双击调整大小
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingPB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingPB.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String url = request.getUrl().toString();
                    Log.w("看看是不是url", url);
                    view.loadUrl(url);
                }
                return true;
            }
        });


        backWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        im_setSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });

    }


    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("调字体大小");

        String[] items = new String[]{"大", "中", "小"};
        builder.setSingleChoiceItems(items, myCurrentTextSizeWich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myTextSizeWich = which;
                Log.w("当前which", String.valueOf(myTextSizeWich));
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                WebSettings settings = webView.getSettings();
                switch (myTextSizeWich) {
                    case 0:
                        //大
                        settings.setTextZoom(200);
                        break;
                    case 1:
                        settings.setTextZoom(100);
                        break;
                    case 2:
                        settings.setTextZoom(50);
                        break;
                    default:
                        settings.setTextZoom(100);
                        break;
                }
                myCurrentTextSizeWich = myTextSizeWich;
            }
        });

        builder.setNegativeButton("取消", null);
        builder.show();
    }

}
