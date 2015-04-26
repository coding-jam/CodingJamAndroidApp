package it.cosenonjaviste.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.Dagger2CnjFragment;
import it.cosenonjaviste.ObjectsMapRetainedFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.lib.mvp.MvpPresenter;

public class PageFragment extends Dagger2CnjFragment<PageModel> {

    @InjectView(R.id.web_view) WebView webView;

    @InjectView(R.id.progress_detail) View progressBar;

    @Inject PagePresenter presenter;

    @Override protected MvpPresenter<PageModel> injectAndCreatePresenter() {
        ObjectsMapRetainedFragment.getOrCreate(
                getChildFragmentManager(),
                PageFragment.class.getName(),
                () -> DaggerPageComponent.builder().applicationComponent(CoseNonJavisteApp.getComponent(getActivity())).build()
        ).inject(this);
        return presenter;
    }

    @SuppressLint("SetJavaScriptEnabled") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_detail, container, false);
        ButterKnife.inject(this, view);

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
            public boolean shouldOverrideUrlLoading(WebView view11, String url) {
                view11.loadUrl(url);
                return true;
            }

            @Override public WebResourceResponse shouldInterceptRequest(WebView view11, String url) {
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
                return super.shouldInterceptRequest(view11, url);
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return new WebResourceResponse("text/css", "UTF-8", getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public void onPageFinished(WebView view11, String url) {
                super.onPageFinished(view11, url);
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    @Override public void update(PageModel model) {
        webView.loadUrl(presenter.getPostUrl());
    }
}
