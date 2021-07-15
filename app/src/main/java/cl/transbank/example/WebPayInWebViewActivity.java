package cl.transbank.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebPayInWebViewActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_pay_in_web_view_message);
        webView = (WebView) findViewById(R.id.webPayWebView);
        loadWebPay(888);
    }

    private void closeWebView(){
        webView.setVisibility(View.INVISIBLE);
        webView.loadUrl("");
    }

    private void loadWebPay(int productId){
        String url = "http://10.0.2.2:3000/webpay-plus/create?from=web_view&producId=" + productId;

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
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                }
                return true;//Indicates WebView to NOT load the url;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("onPageFinished", "===========================================================================================================");
                Log.i("=====", url);
                Log.i("onPageFinished", "===========================================================================================================");
                if ("http://10.0.2.2:3000/webpay-plus/commit?from=web_view".equals(url)){
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