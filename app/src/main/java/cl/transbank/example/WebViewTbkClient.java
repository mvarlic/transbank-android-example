package cl.transbank.example;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class WebViewTbkClient extends WebViewClient {
    private String PREFIX = "intent://";
    private String EMPTY_STRING = "";
    private String FALLBACK_URL = "browser_fallback_url";
    private String TAG = "WebViewTbkClient";
    private String CONTEXT_ERROR_MESSAGE = "Se necesita el contexto para utilizar esta clase";
    private String FALLBACK_ERROR_MESSAGE = "Se necesita el parametro fallback_url en el Bundle del Intent";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (isValidUrl(url)) {
            try {
                Context context = getContextFromWebView(view);
                Intent intent = getIntentFromURL(url);
                view.stopLoading();
                goToNewActivity(context, intent, view);
            } catch (Exception ex) {
                saveMessageLog(ex.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }



    private Intent getIntentFromURL(String url) throws URISyntaxException {
        return Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
    }

    private Context getContextFromWebView(WebView view) {
        if (view.getContext() == null)
            throw new RuntimeException(CONTEXT_ERROR_MESSAGE);
        return view.getContext();
    }

    private void goToNewActivity(Context context, Intent intent, WebView view) {
        ResolveInfo info = obtainInfoFromIntent(context, intent);
        if (info != null) {
            context.startActivity(intent);
        } else {
            String fallbackUrl = intent.getStringExtra(FALLBACK_URL);
            if (fallbackUrl == null)
               throw new RuntimeException(FALLBACK_ERROR_MESSAGE);
            view.loadUrl(fallbackUrl);
        }
    }

    private ResolveInfo obtainInfoFromIntent(Context context, Intent intent) {
        return context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
    }

    private void saveMessageLog(String message) {
        Log.e(TAG, message);
    }

    private Boolean isValidUrl(String url) {
        return url.startsWith(PREFIX) ? true : false;
    }
}
