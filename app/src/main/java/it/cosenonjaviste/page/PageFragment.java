package it.cosenonjaviste.page;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.InjectView;
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;

public class PageFragment extends CnjFragment<PagePresenter, PageModel> {

    @InjectView(R.id.web_view) WebView webView;

    @InjectView(R.id.progress_detail) View progressBar;

    @Inject Provider<PagePresenter> presenterProvider;

    @Override protected PagePresenter createPresenter() {
        return presenterProvider.get();
    }

    @SuppressLint("SetJavaScriptEnabled") @Override protected void initView(View view) {
        super.initView(view);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        File externalFilesDir = getActivity().getExternalFilesDir(null);
        if (externalFilesDir != null) {
            settings.setAppCachePath(externalFilesDir.getAbsolutePath());
            settings.setAppCacheMaxSize(20 * 1024 * 1024);
            settings.setAppCacheEnabled(true);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.equalsIgnoreCase("http://www.cosenonjaviste.it/wp-content/themes/flexform/style.css")) {
                    return getCssWebResourceResponseFromAsset();
                }
                if (url.startsWith("https://pbs.twimg.com/")
                        || url.startsWith("https://cdn.syndication.twimg.com/")
                        || url.startsWith("https://syndication.twitter.com")
                        || url.contains("platform.twitter.com/")
                        || url.startsWith("http://www.facebook.com/plugins/like_box.php")
                        || url.startsWith("https://fbcdn-profile-")
                        || url.contains("sharethis.com/")
                        || url.equals("http://www.cosenonjaviste.it/wp-content/uploads/2013/06/favicon.ico")
                        ) {
                    return null;
                }
                return super.shouldInterceptRequest(view, url);
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return new WebResourceResponse("text/css", "UTF-8", getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override protected int getLayoutId() {
        return R.layout.post_detail;
    }

    @Override public void update(PageModel model) {
        webView.loadUrl(presenter.getPostUrl());
    }
}
