package cl.transbank.example;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebPayInWebViewActivity extends AppCompatActivity {

    private WebView webView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_pay_in_web_view_message);
        webView = (WebView) findViewById(R.id.webPayWebView);
        loadWebPay2(888);
    }

    private void closeWebView(){
        webView.setVisibility(View.INVISIBLE);
        webView.loadUrl("");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(WebView webView, String url) {
        webView.setWebViewClient(new WebViewTbkClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(url);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadWebPay2(int productId){
        String url = "https://transbank-android-backend-node.herokuapp.com/webpay-plus/create?from=web_view&producId=" + productId;
        webView = (WebView) findViewById(R.id.webPayWebView);
        setupWebView(webView, url);
    }

    private void loadWebPay(int productId){
        String url = "https://transbank-android-backend-node.herokuapp.com/webpay-plus/create?from=web_view&producId=" + productId;

        webView = (WebView) findViewById(R.id.webPayWebView);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code
                Log.i("UrlLoading", "===========================================================================================================");
                Log.i("UrlLoading", url);
                Log.i("UrlLoading", "===========================================================================================================");

                if( URLUtil.isNetworkUrl(url) ) {
                    return false; //Allow WebView to load url
                }
                else {
                    Log.i("=====entrandoooooooo", url);
                    try{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity( intent );//NOTA: arrojara excepcion si no encuentra el app a abrir
                    }
                    catch(Exception ex){
                        Log.e("abriendo", url);
                        Log.e("abriendo", ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                return true;//Indicates WebView to NOT load the url;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("onPageFinished", "===========================================================================================================");
                Log.i("=====", url);
                Log.i("onPageFinished", "===========================================================================================================");
                if ("https://transbank-android-backend-node.herokuapp.com/webpay-plus/commit?from=web_view".equals(url)){
                    closeWebView();
                    finishPay();
                }
            }
        });

        // habilitar el Cookie Manager. Depende del nivel de la API de Android que se utilice se habilita de diferente forma
        /*
        if (android.os.Build.VERSION.SDK_INT >= 21)
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true); // myWebPayView es el WebView
        else
            CookieManager.getInstance().setAcceptCookie(true);
        // Asignar el caché en el webview
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);*/

        webView.loadUrl(url);
    }

    public void regresar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void finishPay() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}